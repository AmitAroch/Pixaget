package com.kalevet.pixaget.exceptions

const val CATEGORY_NOT_SUPPORTED_EXCEPTION_MESSAGE = "Category only accepted values are: \"backgrounds\", \"fashion\", \"nature\", \"science\", \"education\", \"feelings\", \"health\", \"people\", \"religion\", \"places\", \"animals\", \"industry\", \"computer\", \"food\", \"sports\", \"transportation\", \"travel\", \"buildings\", \"business\", \"music\". category was: {"
val SUPPORTED_CATEGORIES = arrayOf("backgrounds", "fashion", "nature", "science", "education", "feelings", "health", "people", "religion", "places", "animals", "industry", "computer", "food", "sports", "transportation", "travel", "buildings", "business", "music")


class CategoryNotSupportedException : PixabayException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        fun generateMessage(category: String): String =
            "$CATEGORY_NOT_SUPPORTED_EXCEPTION_MESSAGE$category}"
    }

}
