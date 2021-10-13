package com.kalevet.pixaget.exceptions

const val PER_PAGE_OUT_OF_RANGE_EXCEPTION_MESSAGE = "Per page must be between 3-200. per_page was: {"

class PerPageOutOfRangeException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        fun generateMessage(perPage: Int): String =
            "$PER_PAGE_OUT_OF_RANGE_EXCEPTION_MESSAGE$perPage}"
    }

}
