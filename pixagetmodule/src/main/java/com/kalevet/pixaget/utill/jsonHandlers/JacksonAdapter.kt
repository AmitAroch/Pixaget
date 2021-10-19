package com.kalevet.pixaget.utill.jsonHandlers

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.Reader

class JacksonAdapter : JsonConverter {

    private val objectMapper: ObjectMapper

    init {
        try {
            objectMapper = ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(
                    KotlinModule.Builder().build()
                )
        }catch (e: NoClassDefFoundError){
            throw NoClassDefFoundError("The Jackson Object Mapper class was not found, try adding this: 'implementation(\"com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0\")' to your app's build.gradle file")
        }
    }

    override fun <T> convert(jsonReader: Reader, classOfT: Class<T>): T {
        return objectMapper.readValue(jsonReader, classOfT)
    }
}