package com.kalevet.pixaget.data.repositories.remote.jsonAdapters

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kalevet.pixaget.data.repositories.remote.JsonConverter
import java.io.Reader

class JacksonAdapter : JsonConverter {

    val objectMapper = ObjectMapper()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .registerModule(
            KotlinModule.Builder().build()
        )

    override fun <T> convert(jsonReader: Reader, classOfT: Class<T>): T? {
        return objectMapper.readValue(jsonReader, classOfT)
    }
}