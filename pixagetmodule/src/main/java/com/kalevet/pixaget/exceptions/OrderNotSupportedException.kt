package com.kalevet.pixaget.exceptions

const val ORDER_NOT_SUPPORTED_EXCEPTION_MESSAGE = "Order only accepted values are: \"popular\", \"latest\". order was: {"
val SUPPORTED_ORDER = arrayOf("popular", "latest")

class OrderNotSupportedException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        fun generateMessage(order: String): String =
            "$ORDER_NOT_SUPPORTED_EXCEPTION_MESSAGE$order}"
    }

}
