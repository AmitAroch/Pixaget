package com.kalevet.pixaget.data.repositories.remote.jsonAdapters

import com.google.gson.Gson
import com.kalevet.pixaget.data.repositories.remote.JsonConverter
import java.io.Reader

class GsonAdapter: JsonConverter {

    val gson: Gson = Gson()

    override fun <T> convert(jsonReader: Reader, classOfT: Class<T>): T? {
        return gson.fromJson(jsonReader, classOfT)
    }
}