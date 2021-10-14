package com.kalevet.pixaget.exceptions


const val HTTP_CLIENT_IS_MISSING_EXCEPTION_MESSAGE = "Pixaget builder must be provided with a http client in order to function.\n" +
        "You can use Retrofit2 by calling Pixaget.Builder().setRetrofitBuilder(Retrofit.Builder()), or you can use OkHttp by calling Pixaget.Builder().setOkHttpClient(okHttpClient()) " +
        " Calling both methods ( setRetrofitBuilder(), setOkHttpClient() ) will use Retrofit with the OkHttpClient (retrofitBuilder.client(okHttpClient))."

class HttpClientIsMissingException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        fun generateMessage(): String = HTTP_CLIENT_IS_MISSING_EXCEPTION_MESSAGE
    }
}
