package com.kalevet.pixaget.data.repositories.remote.jsonAdapters

import com.kalevet.pixaget.data.repositories.remote.JsonConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.Reader

class MoshiAdapter: JsonConverter {

    val moshi = Moshi.Builder().build()

    override fun <T> convert(jsonReader: Reader, classOfT: Class<T>): T? {
        val adapter: JsonAdapter<T> = moshi.adapter(classOfT)
        return adapter.fromJson(readerToString(jsonReader))
    }

    fun readerToString(reader: Reader): String {
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