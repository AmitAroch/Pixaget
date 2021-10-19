package com.kalevet.pixagetapp.di

import com.kalevet.pixaget.Pixaget
import com.kalevet.pixagetapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object PixabayProvider {

    private const val apiKey = BuildConfig.PIXABAY_API_KEY

    /*@Singleton
    @Provides
    fun providePixabay(): Pixaget {
        return Pixaget.RetrofitBuilder()
            .setPixabayApiKey(apiKey)
            .setConverter(Pixaget.ConverterOptions.MOSHI)
            //.setConverter(Pixaget.ConverterOptions.JACKSON)
            //.setConverter(Pixaget.ConverterOptions.GSON)
            .setRetrofitBuilder(Retrofit.Builder())
            //.setOkHttpClient(OkHttpClient())
            .build()
    }*/

    @Singleton
    @Provides
    fun providePixabay(): Pixaget {
        return Pixaget.OkHttpBuilder()
            .setPixabayApiKey(apiKey)
            .setConverter(Pixaget.ConverterOptions.MOSHI)
            //.setConverter(Pixaget.ConverterOptions.JACKSON)
            //.setConverter(Pixaget.ConverterOptions.GSON)
            .setOkHttpClient(OkHttpClient())
            .build()
    }
}