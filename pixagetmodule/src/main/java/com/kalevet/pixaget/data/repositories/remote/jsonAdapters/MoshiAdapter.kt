package com.kalevet.pixaget.data.repositories.remote.jsonAdapters

import com.kalevet.pixaget.data.repositories.remote.JsonConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.Reader

class MoshiAdapter: JsonConverter {

    private val moshi:Moshi

    init {
        try {
            moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        }catch (e: NoClassDefFoundError){
            throw NoClassDefFoundError("The Moshi builder class was not found, try adding this: 'implementation(\"com.squareup.moshi:moshi-kotlin:1.12.0\")' to your app's build.gradle file")
        }
    }

    override fun <T> convert(jsonReader: Reader, classOfT: Class<T>): T {
        val adapter: JsonAdapter<T> = moshi.adapter(classOfT)
        val result = adapter.fromJson(readerToString(jsonReader)) ?: throw JsonDataException()
        return result
    }

    private fun readerToString(reader: Reader): String {
        val arr = CharArray(8 * 1024)
        val buffer = StringBuilder()
        var numCharsRead: Int
        while (reader.read(arr, 0, arr.size).also { numCharsRead = it } != -1) {
            buffer.append(arr, 0, numCharsRead)
        }
        reader.close()
        return buffer.toString()
    }
}