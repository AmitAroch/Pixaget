package com.kalevet.pixaget.exceptions

import okhttp3.Response


/** Exception for an unexpected, non-2xx HTTP response.  */
class HttpException(response: Response) : RuntimeException() {
    /** HTTP status code.  */
    val code: Int = response.code
    /** HTTP status message.  */
    override val message: String = response.message

}