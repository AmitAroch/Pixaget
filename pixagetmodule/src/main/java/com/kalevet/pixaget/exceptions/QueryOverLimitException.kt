package com.kalevet.pixaget.exceptions

const val QUERY_OVER_LIMIT_EXCEPTION_MESSAGE = "Query must not exceed 100 characters, query lenght was: {"

class QueryOverLimitException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)


    companion object {
        fun generateMessage(queryLength: Int): String =
            "$QUERY_OVER_LIMIT_EXCEPTION_MESSAGE$queryLength}"
    }

}
