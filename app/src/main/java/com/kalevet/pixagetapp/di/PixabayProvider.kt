package com.kalevet.pixagetapp.di

import com.kalevet.pixaget.Pixaget
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

    private const val apiKey = "23570191-4b9d123947a3c70a11419dfbe"

    @Singleton
    @Provides
    fun providePixabay(): Pixaget {
        return Pixaget.Builder()
            .setPixabayApiKey(apiKey)
            .setConverter(Pixaget.ConverterOptions.GSON)
            .setOkHttpClient(OkHttpClient())
            .build()
    }
}