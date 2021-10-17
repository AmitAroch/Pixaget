package com.kalevet.pixaget.data.repositories.remote

import java.io.Reader

interface JsonConverter {

    fun <T> convert(jsonReader: Reader, classOfT: Class<T>): T

}