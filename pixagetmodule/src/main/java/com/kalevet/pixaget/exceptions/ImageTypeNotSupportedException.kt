package com.kalevet.pixaget.exceptions


const val IMAGE_TYPE_NOT_SUPPORTED_EXCEPTION_MESSAGE = "ImageType only accepted values are: \"all\", \"photo\", \"illustration\", \"vector\". Video_type was: {"
val SUPPORTED_IMAGE_TYPES = arrayOf("all", "photo", "illustration", "vector")

class ImageTypeNotSupportedException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        fun generateMessage(videoType: String): String =
            "$IMAGE_TYPE_NOT_SUPPORTED_EXCEPTION_MESSAGE$videoType}"
    }

}
