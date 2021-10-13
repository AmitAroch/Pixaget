package com.kalevet.pixaget.exceptions


const val API_KEY_IS_MISSING_EXCEPTION_MESSAGE = "Pixabay builder must be provided with a pixabay API key in order to function, pixabayApiKey was {null}. call Pixabay.Builder().pixabayApiKey({PROVIDED_API_KEY})"

class PixabayApiKeyIsMissingException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        fun generateMessage(): String = API_KEY_IS_MISSING_EXCEPTION_MESSAGE
    }
}
