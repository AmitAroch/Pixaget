package com.kalevet.pixaget.utill.jsonHandlers

import com.google.gson.Gson
import java.io.Reader

class GsonAdapter: JsonConverter {

    private val gson: Gson

    init {
        try {
            gson = Gson()
        }catch (e: NoClassDefFoundError){
            throw NoClassDefFoundError("Gson class was not found, try adding this: 'implementation(\"com.google.code.gson:gson:2.8.8\")' to your app's build.gradle file")
        }
    }
    override fun <T> convert(jsonReader: Reader, classOfT: Class<T>): T {
        return gson.fromJson(jsonReader, classOfT)
    }
}