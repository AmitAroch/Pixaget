package com.kalevet.pixaget.utill.jsonHandlers

import java.io.Reader

interface JsonConverter {

    fun <T> convert(jsonReader: Reader, classOfT: Class<T>): T

}