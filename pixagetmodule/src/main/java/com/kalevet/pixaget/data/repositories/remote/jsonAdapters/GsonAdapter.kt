package com.kalevet.pixaget.data.repositories.remote.jsonAdapters

import com.google.gson.Gson
import com.kalevet.pixaget.data.repositories.remote.JsonConverter
import java.io.Reader

class GsonAdapter: JsonConverter {

    private val gson: Gson

    init {
        try {
            gson = Gson()
        }catch (e: NoClassDefFoundError){
            throw NoClassDefFoundError("The Moshi builder class was not found, try adding this: 'implementation(\"com.google.code.gson:gson:2.8.8\")' to your app's build.gradle file")
        }
    }
    override fun <T> convert(jsonReader: Reader, classOfT: Class<T>): T {
        return gson.fromJson(jsonReader, classOfT)
    }
}