package com.kalevet.pixaget


import com.kalevet.pixaget.data.repositories.remote.apiServices.OkHttpClientAdapter
import com.kalevet.pixaget.data.repositories.remote.apiServices.PixabayApiService
import com.kalevet.pixaget.data.repositories.remote.apiServices.RetroftAdapter
import com.kalevet.pixaget.data.repositories.remote.jsonAdapters.GsonAdapter
import com.kalevet.pixaget.data.repositories.remote.jsonAdapters.JacksonAdapter
import com.kalevet.pixaget.data.repositories.remote.jsonAdapters.MoshiAdapter
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest
import com.kalevet.pixaget.data.repositories.remote.requests.VideoSearchRequest
import com.kalevet.pixaget.data.repositories.remote.responses.ImageSearchResult
import com.kalevet.pixaget.data.repositories.remote.responses.VideoSearchResult
import com.kalevet.pixaget.exceptions.HttpClientIsMissingException
import com.kalevet.pixaget.exceptions.JsonConverterIsMissingException
import com.kalevet.pixaget.exceptions.PixabayApiKeyIsMissingException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * The Pixabay class provide an easy way to generate a Pixabay server's search request and provide the response
 */
@ExperimentalCoroutinesApi
class Pixabay private constructor(
    private val pixabayApiKey: String,
    private val pixabayApiService: PixabayApiService
) {
    enum class ConverterOptions {
        GSON,
        JACKSON,
        MOSHI,
    }

    /**
     * A builder patterned class
     * @param pixabayApiKey is a api key required to request videos/images from the Pixabay server.
     *                       if you do not have an api key you can get one for free at @see <a href="https://pixabay.com/api/docs/">Pixabay.com</a>
     */
    data class Builder(
        private var pixabayApiKey: String? = null,
        private var pixabayApiService: PixabayApiService? = null,
        private var converterOptions: ConverterOptions? = null,

        private var okHttpClient: OkHttpClient? = null,
        private var retrofitBuilder: Retrofit.Builder? = null,
    ) {

        /**
         * Set the Pixabay api key that allows you to access the Pixabay service api, to get this
         * key @see <a href="https://pixabay.com/api/docs/">Pixabay.com</a>.
         *
         * @param pixabayApiKey A string representing the Pixabay api key, this key can be obtained at the Pixabay website.
         */
        fun setPixabayApiKey(pixabayApiKey: String) = apply { this.pixabayApiKey = pixabayApiKey }

        /**
         * Set the library that will convert the Pixabay's server json response into a kotlin result object.
         *
         * @param converterOptions @see ConverterOptions for json to object converterOptions options
         */
        fun setConverter(converterOptions: ConverterOptions) = apply {
            this.converterOptions = converterOptions
        }

        fun setOkHttpClient(client: OkHttpClient) = apply { this.okHttpClient = client }

        fun setRetrofitBuilder(retrofitBuilder: Retrofit.Builder) = apply { this.retrofitBuilder = retrofitBuilder }


        /**
         * Set the Pixabay api key that allows you to access the Pixabay service api, to get this
         * key @see <a href="https://pixabay.com/api/docs/">Pixabay.com</a>
         *
         * @return a Pixabay object if Pixabay api key was provided.
         * @throws ClassNotFoundException if OkHttpClient class was not added to the dependencies.
         */
        @Throws(PixabayApiKeyIsMissingException::class, JsonConverterIsMissingException::class)
        fun build(): Pixabay {

            pixabayApiKey ?: throw PixabayApiKeyIsMissingException()
            converterOptions ?: throw JsonConverterIsMissingException()



            retrofitBuilder?.let { retrofitBuilder ->
                val converterFactory = when (converterOptions!!) {
                    ConverterOptions.GSON -> GsonConverterFactory.create()
                    ConverterOptions.JACKSON -> JacksonConverterFactory.create()
                    ConverterOptions.MOSHI -> MoshiConverterFactory.create()
                }
                if (okHttpClient != null){
                    pixabayApiService = RetroftAdapter(retrofitBuilder, converterFactory, okHttpClient)
                }else {
                    pixabayApiService = RetroftAdapter(retrofitBuilder, converterFactory)
                }
            } ?: okHttpClient?.let { client ->
                val jsonConverter = when (converterOptions!!) {
                    ConverterOptions.GSON -> GsonAdapter()
                    ConverterOptions.JACKSON -> JacksonAdapter()
                    ConverterOptions.MOSHI -> MoshiAdapter()
                }
                pixabayApiService = OkHttpClientAdapter(client, jsonConverter)
            } ?: throw HttpClientIsMissingException()

            return Pixabay(pixabayApiKey!!, pixabayApiService!!)
        }
    }

    /**
     * @param imageSearchSearchRequest an Pixabay search request object filters the search to the required results.
     * @return ImageSearchResult in case of success,
     */
    suspend fun sendSearchRequest(imageSearchSearchRequest: ImageSearchRequest): ImageSearchResult? {
        imageSearchSearchRequest.apiKey = pixabayApiKey
        return pixabayApiService.sendRequest(imageSearchSearchRequest) as ImageSearchResult?
    }


    /**
     * @param videoSearchRequest an Pixabay search request object filters the search to the required results.
     * @return VideoSearchResult in case of success,
     */
    suspend fun sendSearchRequest(videoSearchRequest: VideoSearchRequest): VideoSearchResult? {
        videoSearchRequest.apiKey = pixabayApiKey
        return pixabayApiService.sendRequest(videoSearchRequest) as VideoSearchResult?
    }

}