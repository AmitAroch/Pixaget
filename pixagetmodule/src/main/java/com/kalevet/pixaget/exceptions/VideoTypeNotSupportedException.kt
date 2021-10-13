package com.kalevet.pixaget.exceptions

const val VIDEO_TYPE_NOT_SUPPORTED_EXCEPTION_MESSAGE = "videoType only accepted values are: \"all\", \"film\", \"animation\". Video_type was: {"
val SUPPORTED_VIDEO_TYPES = arrayOf("cs", "da", "de", "en", "es", "fr", "id", "it", "hu", "nl", "no", "pl", "pt", "ro", "sk", "fi", "sv", "tr", "vi", "th", "bg", "ru", "el", "ja", "ko", "zh")

class VideoTypeNotSupportedException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        fun generateMessage(videoType: String): String =
            "$VIDEO_TYPE_NOT_SUPPORTED_EXCEPTION_MESSAGE$videoType}"
    }

}
