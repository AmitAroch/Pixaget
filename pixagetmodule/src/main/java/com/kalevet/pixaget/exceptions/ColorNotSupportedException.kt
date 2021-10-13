package com.kalevet.pixaget.exceptions

const val COLOR_NOT_SUPPORTED_EXCEPTION_MESSAGE = "Colors only accepted values are:  \"grayscale\", \"transparent\", \"red\", \"orange\", \"yellow\", \"green\", \"turquoise\", \"blue\", \"lilac\", \"pink\", \"white\", \"gray\", \"black\", \"brown\". colors was: {"
val SUPPORTED_COLORS = arrayOf( "grayscale", "transparent", "red", "orange", "yellow", "green", "turquoise", "blue", "lilac", "pink", "white", "gray", "black", "brown")

class ColorNotSupportedException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        fun generateMessage(videoType: String): String =
            "$COLOR_NOT_SUPPORTED_EXCEPTION_MESSAGE$videoType}"
    }

}
