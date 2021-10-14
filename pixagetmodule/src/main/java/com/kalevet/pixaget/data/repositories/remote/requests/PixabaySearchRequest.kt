package com.kalevet.pixaget.data.repositories.remote.requests

import com.kalevet.pixaget.data.repositories.remote.responses.PixabaySearchResult

interface PixabaySearchRequest<T : PixabaySearchResult<*>> {

    var apiKey: String

    fun buildRequestUrl(): String

    fun buildRequestQueryMap(): Map<String, String>

    fun getResponseClassType(): Class<T>

    enum class Category { all, backgrounds, fashion, nature, science, education, feelings, health, people, religion, places, animals, industry, computer, food, sports, transportation, travel, buildings, business, music }
    enum class Colors { all, grayscale, transparent, red, orange, yellow, green, turquoise, blue, lilac, pink, white, gray, black, brown }
    enum class ImageTypes { all, photo, illustration, vector }
    enum class Order(val value: Boolean) { popular(false), latest(true) }
    enum class Orientation(val value: Boolean?) { all(null), horizontal(true), vertical(false) }
    enum class VideoType { all, film, animation }
    enum class Language(val value: String) {
        Czech("cs"), Danish("da"), Deutsch("de"), English("en"), Spanish("es"), French("fr"), Indonesian(
            "id"
        ),
        Italian("it"), Hungarian("hu"), Dutch("nl"), Norwegian("no"), Polish("pl"), Portuguese("pt"), Romanian(
            "ro"
        ),
        Slovak("sk"), Finnish("fi"), Swedish("sv"), Turkish("tr"), Vietnamese("vi"), Thai("th"), Bulgarian(
            "bg"
        ),
        Russian("ru"), Greek("el"), Japanese("ja"), Korean("ko"), Chinese("zh");
    }

}