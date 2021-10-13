package com.kalevet.pixaget.exceptions


const val JSON_PARSER_IS_MISSING_EXCEPTION_MESSAGE = "Pixabay builder must be provided with a json converter in order to function.\n" +
        " call Pixabay.Builder().setConverter({Pixabay.ConverterOptions}), where Pixabay.ConverterOptions is one of the following: GSON, JACKSON, MOSHI."

class JsonConverterIsMissingException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        fun generateMessage(): String = JSON_PARSER_IS_MISSING_EXCEPTION_MESSAGE
    }
}
