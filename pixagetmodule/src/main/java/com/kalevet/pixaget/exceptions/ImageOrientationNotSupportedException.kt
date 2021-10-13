package com.kalevet.pixaget.exceptions

const val IMAGE_ORIENTATION_NOT_SUPPORTED_EXCEPTION_MESSAGE = "Orientation only accepted values are:  \"all\", \"horizontal\", \"vertical\". orientation was: {"
val SUPPORTED_IMAGE_ORIENTATIONS = arrayOf( "all", "horizontal", "vertical")


class ImageOrientationNotSupportedException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        fun generateMessage(videoType: String): String =
            "$IMAGE_ORIENTATION_NOT_SUPPORTED_EXCEPTION_MESSAGE$videoType}"
    }

}
