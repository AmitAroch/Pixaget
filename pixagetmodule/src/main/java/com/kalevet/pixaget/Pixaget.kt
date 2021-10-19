package com.kalevet.pixaget


import com.kalevet.pixaget.data.repositories.remote.apiServices.OkHttpClientAdapter
import com.kalevet.pixaget.data.repositories.remote.apiServices.PixabayApiService
import com.kalevet.pixaget.data.repositories.remote.apiServices.RetrofitAdapter
import com.kalevet.pixaget.data.repositories.remote.requests.ImageSearchRequest
import com.kalevet.pixaget.data.repositories.remote.requests.VideoSearchRequest
import com.kalevet.pixaget.data.repositories.remote.responses.ImageSearchResult
import com.kalevet.pixaget.data.repositories.remote.responses.VideoSearchResult
import com.kalevet.pixaget.exceptions.HttpClientIsMissingException
import com.kalevet.pixaget.exceptions.JsonConverterIsMissingException
import com.kalevet.pixaget.exceptions.PixabayApiKeyIsMissingException
import com.kalevet.pixaget.utill.jsonHandlers.GsonAdapter
import com.kalevet.pixaget.utill.jsonHandlers.JacksonAdapter
import com.kalevet.pixaget.utill.jsonHandlers.MoshiAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * The Pixaget class provide an easy way to generate a Pixaget server's search request and provide the response
 */
@ExperimentalCoroutinesApi
class Pixaget private constructor(
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
     * @param pixabayApiKey is a api key required to request videos/images from the Pixaget server.
     *                       if you do not have an api key you can get one for free at @see <a href="https://pixabay.com/api/docs/">Pixaget.com</a>
     */
    /*data class Builder(
        private var pixabayApiKey: String? = null,
        private var pixabayApiService: PixabayApiService? = null,
        private var converterOptions: ConverterOptions? = null,

        private var okHttpClient: OkHttpClient? = null,
        private var retrofitBuilder: Retrofit.Builder? = null,
    ) {

        /**
         * Set the Pixaget api key that allows you to access the Pixaget service api, to get this
         * key @see <a href="https://pixabay.com/api/docs/">Pixaget.com</a>.
         *
         * @param pixabayApiKey A string representing the Pixaget api key, this key can be obtained at the Pixaget website.
         */
        fun setPixabayApiKey(pixabayApiKey: String) = apply { this.pixabayApiKey = pixabayApiKey }

        /**
         * Set the library that will convert the Pixaget's server json response into a kotlin result object.
         *
         * @param converterOptions @see ConverterOptions for json to object converterOptions options
         */
        fun setConverter(converterOptions: ConverterOptions) = apply {
            this.converterOptions = converterOptions
        }

        fun setOkHttpClient(client: OkHttpClient) = apply { this.okHttpClient = client }

        fun setRetrofitBuilder(retrofitBuilder: Retrofit.Builder) = apply { this.retrofitBuilder = retrofitBuilder }


        /**
         * Set the Pixaget api key that allows you to access the Pixaget service api, to get this
         * key @see <a href="https://pixabay.com/api/docs/">Pixaget.com</a>
         *
         * @return a Pixaget object if Pixaget api key was provided.
         * @throws ClassNotFoundException if OkHttpClient class was not added to the dependencies.
         */
        @Throws(PixabayApiKeyIsMissingException::class, JsonConverterIsMissingException::class)
        fun build(): Pixaget {

            pixabayApiKey ?: throw PixabayApiKeyIsMissingException()
            converterOptions ?: throw JsonConverterIsMissingException()



            retrofitBuilder?.let { retrofitBuilder ->
                val converterFactory = when (converterOptions!!) {
                    ConverterOptions.GSON -> GsonConverterFactory.create()
                    ConverterOptions.JACKSON -> JacksonConverterFactory.create()
                    ConverterOptions.MOSHI -> MoshiConverterFactory.create()
                }
                if (okHttpClient != null){
                    pixabayApiService = RetrofitAdapter(retrofitBuilder, converterFactory, okHttpClient)
                }else {
                    pixabayApiService = RetrofitAdapter(retrofitBuilder, converterFactory)
                }
            } ?: okHttpClient?.let { client ->
                val jsonConverter = when (converterOptions!!) {
                    ConverterOptions.GSON -> GsonAdapter()
                    ConverterOptions.JACKSON -> JacksonAdapter()
                    ConverterOptions.MOSHI -> MoshiAdapter()
                }
                pixabayApiService = OkHttpClientAdapter(client, jsonConverter)
            } ?: throw HttpClientIsMissingException()

            return Pixaget(pixabayApiKey!!, pixabayApiService!!)
        }
    }
    */

    data class RetrofitBuilder(
        private var pixabayApiKey: String? = null,
        private var converterOptions: ConverterOptions? = null,
        private var okHttpClient: OkHttpClient? = null,
        private var retrofitBuilder: Retrofit.Builder? = null,
    ) {

        /**
         * Set the Pixaget api key that allows you to access the Pixaget service api, to get this
         * key @see <a href="https://pixabay.com/api/docs/">Pixaget.com</a>.
         *
         * @param pixabayApiKey A string representing the Pixaget api key, this key can be obtained at the Pixaget website.
         */
        fun setPixabayApiKey(pixabayApiKey: String) = apply { this.pixabayApiKey = pixabayApiKey }

        /**
         * Set the library that will convert the Pixaget's server json response into a kotlin result object.
         *
         * @param converterOptions @see ConverterOptions for json to object converterOptions options
         */
        fun setConverter(converterOptions: ConverterOptions) = apply {
            this.converterOptions = converterOptions
        }

        fun setOkHttpClient(client: OkHttpClient) = apply { this.okHttpClient = client }

        fun setRetrofitBuilder(retrofitBuilder: Retrofit.Builder) =
            apply { this.retrofitBuilder = retrofitBuilder }


        /**
         * Set the Pixaget api key that allows you to access the Pixaget service api, to get this
         * key @see <a href="https://pixabay.com/api/docs/">Pixaget.com</a>
         *
         * @return a Pixaget object if Pixaget api key was provided.
         * @throws ClassNotFoundException if OkHttpClient class was not added to the dependencies.
         */
        @Throws(PixabayApiKeyIsMissingException::class, JsonConverterIsMissingException::class)
        fun build(): Pixaget {

            pixabayApiKey ?: throw PixabayApiKeyIsMissingException()
            converterOptions ?: throw JsonConverterIsMissingException()

            retrofitBuilder?.let { retrofitBuilder ->
                val converterFactory = when (converterOptions!!) {
                    ConverterOptions.GSON -> try {
                        GsonConverterFactory.create()
                    } catch (e: NoClassDefFoundError) {
                        throw NoClassDefFoundError("The GsonConverterFactory class was not found, try adding this: 'implementation(\"com.squareup.retrofit2:converter-gson:2.9.0\")' to your app's build.gradle file")
                    }
                    ConverterOptions.JACKSON -> try {
                        JacksonConverterFactory.create()
                    } catch (e: NoClassDefFoundError) {
                        throw NoClassDefFoundError("The JacksonConverterFactory class was not found, try adding this: 'implementation(\"com.squareup.retrofit2:converter-jackson:2.9.0\")' to your app's build.gradle file")
                    }
                    ConverterOptions.MOSHI -> try {
                        MoshiConverterFactory.create()
                    } catch (e: NoClassDefFoundError) {
                        throw NoClassDefFoundError("The MoshiConverterFactory class was not found, try adding this: 'implementation(\"com.squareup.retrofit2:converter-moshi:2.9.0\")' to your app's build.gradle file")
                    }
                }
                return Pixaget(
                    pixabayApiKey!!,
                    RetrofitAdapter(retrofitBuilder, converterFactory, okHttpClient)
                )
            } ?: throw HttpClientIsMissingException()
        }
    }

    data class OkHttpBuilder(
        private var pixabayApiKey: String? = null,
        private var converterOptions: ConverterOptions? = null,
        private var okHttpClient: OkHttpClient? = null,
    ) {

        /**
         * Set the Pixaget api key that allows you to access the Pixaget service api, to get this
         * key @see <a href="https://pixabay.com/api/docs/">Pixaget.com</a>.
         *
         * @param pixabayApiKey A string representing the Pixaget api key, this key can be obtained at the Pixaget website.
         */
        fun setPixabayApiKey(pixabayApiKey: String) = apply { this.pixabayApiKey = pixabayApiKey }

        /**
         * Set the library that will convert the Pixaget's server json response into a kotlin result object.
         *
         * @param converterOptions @see ConverterOptions for json to object converterOptions options
         */
        fun setConverter(converterOptions: ConverterOptions) = apply {
            this.converterOptions = converterOptions
        }

        fun setOkHttpClient(client: OkHttpClient) = apply { this.okHttpClient = client }

        /**
         * Set the Pixaget api key that allows you to access the Pixaget service api, to get this
         * key @see <a href="https://pixabay.com/api/docs/">Pixaget.com</a>
         *
         * @return a Pixaget object if Pixaget api key was provided.
         * @throws ClassNotFoundException if OkHttpClient class was not added to the dependencies.
         */
        @Throws(PixabayApiKeyIsMissingException::class, JsonConverterIsMissingException::class)
        fun build(): Pixaget {

            pixabayApiKey ?: throw PixabayApiKeyIsMissingException()
            converterOptions ?: throw JsonConverterIsMissingException()

            okHttpClient?.let { client ->
                val jsonConverter = when (converterOptions!!) {
                    ConverterOptions.GSON -> GsonAdapter()
                    ConverterOptions.JACKSON -> JacksonAdapter()
                    ConverterOptions.MOSHI -> MoshiAdapter()
                }
                return Pixaget(pixabayApiKey!!, OkHttpClientAdapter(client, jsonConverter))
            } ?: throw HttpClientIsMissingException()
        }
    }

    /**
     * @param   imageSearchSearchRequest an Pixaget search request object filters the search to the required results.
     * @return  ImageSearchResult in case of success,
     *          null in case of failure
     */
    suspend fun sendSearchRequest(imageSearchSearchRequest: ImageSearchRequest): ImageSearchResult? {
        imageSearchSearchRequest.apiKey = pixabayApiKey
        return pixabayApiService.sendRequest(imageSearchSearchRequest) as ImageSearchResult?
    }


    /**
     * @param   videoSearchRequest an Pixaget search request object filters the search to the required results.
     * @return  VideoSearchResult in case of success,
     *          null in case of failure
     */
    suspend fun sendSearchRequest(videoSearchRequest: VideoSearchRequest): VideoSearchResult? {
        videoSearchRequest.apiKey = pixabayApiKey
        return pixabayApiService.sendRequest(videoSearchRequest) as VideoSearchResult?
    }

}