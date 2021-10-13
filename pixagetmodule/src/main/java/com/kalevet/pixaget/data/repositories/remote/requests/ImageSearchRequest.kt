package com.kalevet.pixaget.data.repositories.remote.requests

import com.kalevet.pixaget.data.repositories.remote.responses.ImageSearchResult
import com.kalevet.pixaget.exceptions.*
import com.kalevet.pixaget.utill.PIXABAY_BASE_URL

/** A class to specify a video search on the Pixabay's repository server using the @see <a href="https://pixabay.com/api/docs/">Pixabay API</a>
 *
 * @param   query   A URL encoded search term. If omitted, all videos
 *                  are returned. This value may not exceed 100 characters.
 *                  Example: "yellow+flower"
 *  @param  lang    <a href="https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes">Language</a> code of the language to be searched in.
 *                  Accepted values: cs, da, de, en, es, fr, id, it, hu,
 *                  nl, no, pl, pt, ro, sk, fi, sv, tr, vi, th, bg, ru,
 *                  el, ja, ko, zh
 *                  Default: "en"
 *  @param  id  Retrieve individual videos by ID.
 *  @param  image_type  Filter results by image type.
 *                      Accepted values: "all", "photo", "illustration", "vector"
 *                      Default: "all"
 *  @param orientation  Whether an image is wider than it is tall, or taller than it is wide.
 *                      Accepted values: "all", "horizontal", "vertical"
 *                      Default: "all"
 *  @param  category    Filter results by category.
 *                      Accepted values: backgrounds, fashion, nature, science, education, feelings,
 *                      health, people, religion, places, animals, industry, computer, food, sports,
 *                      transportation, travel, buildings, business, music
 *  @param  min_width   Minimum video width.
 *                      Default: 0
 *  @param  min_height  Minimum video height.
 *                      Default: 0
 *  @param colors       Filter images by color properties. A comma separated list of values may be used
 *                      to select multiple properties.
 *                      Accepted values: "grayscale", "transparent", "red", "orange", "yellow", "green",
 *                      "turquoise", "blue", "lilac", "pink", "white", "gray", "black", "brown"
 *  @param  editors_choice  Select videos that have received an Editor's Choice award.
 *                          Accepted values: "true", "false"
 *                          Default: "false"
 *  @param  safeSearch  A flag indicating that only videos suitable for all ages should be returned.
 *                      Accepted values: "true", "false"
 *                      Default: "false"
 *  @param  order   How the results should be ordered.
 *                  Accepted values: "popular", "latest"
 *                  Default: "popular"
 *  @param  page    Returned search results are paginated. Use this parameter to select the page number.
 *                  Default: 1
 *  @param  per_page    Determine the number of results per page.
 *                      Accepted values: 3 - 200
 *                      Default: 20
 *  @param  callback    JSONP callback function name
 *  @param  pretty  Indent JSON output. This option should not be used in production.
 *                  Accepted values: "true", "false"
 *                  Default: false
 *
 *  @return a VideoSearchRequest object to be used to create a request from the Pixabay server
 */
data class ImageSearchRequest @JvmOverloads constructor(
    val query: String = String(),
    val lang: String = "en",
    val id: String = String(),
    val image_type: String = "all",
    val orientation: String = "all",
    val category: String = String(),
    val min_width: Int = 0,
    val min_height: Int = 0,
    val colors: String = String(),
    val editors_choice: Boolean = false,
    val safeSearch: Boolean = false,
    val order: String = "popular",
    var page: Int = 0,
    val per_page: Int = 20,
    val callback: String = String(),
    val pretty: Boolean = false
) : PixabaySearchRequest<ImageSearchResult> {

    override var apiKey: String = String()

    override fun buildRequestUrl(): String {
        val builder = StringBuilder()
        builder.append(PIXABAY_BASE_URL)
        builder.append("api/")

        builder.append("?key=$apiKey")

        if (query.isNotEmpty()) {
            if (query.length > 100)
                throw QueryOverLimitException(
                    message = QueryOverLimitException.generateMessage(query.length)
                )
            else {
                builder.append("&q=$query")
            }
        }

        if (lang != "en") {
            if (!SUPPORTED_LANGUAGES.contains(lang))
                throw LanguageNotSupportedException(
                    message = LanguageNotSupportedException.generateMessage(lang)
                )
            else {
                builder.append("&lang=$lang")
            }
        }

        if (id.isNotEmpty()) builder.append("&id=$id")

        if (image_type != "all") {
            if (!SUPPORTED_IMAGE_TYPES.contains(image_type))
                throw ImageTypeNotSupportedException(
                    message = ImageTypeNotSupportedException.generateMessage(image_type)
                )
            else {
                builder.append("&image_type=$image_type")
            }
        }

        if (orientation != "all") {
            if (!SUPPORTED_IMAGE_ORIENTATIONS.contains(orientation))
                throw ImageOrientationNotSupportedException(
                    message = ImageOrientationNotSupportedException.generateMessage(orientation)
                )
            else {
                builder.append("&orientation=$orientation")
            }
        }

        if (category.isNotEmpty()) {
            if (!SUPPORTED_CATEGORIES.contains(category))
                throw CategoryNotSupportedException(
                    message = CategoryNotSupportedException.generateMessage(category)
                )
            else {
                builder.append("&category=$category")
            }
        }

        if (min_width != 0) builder.append("&min_width=$min_width")

        if (min_height != 0) builder.append("&min_width=$min_height")

        if (colors.isNotEmpty()) {
            if (!SUPPORTED_COLORS.contains(colors))
                throw ColorNotSupportedException(
                    message = ColorNotSupportedException.generateMessage(colors)
                )
            else {
                builder.append("&category=$colors")
            }
        }

        if (editors_choice) builder.append("&editors_choice=$editors_choice")

        if (safeSearch) builder.append("&safesearch=$safeSearch")

        if (order != "popular") {
            if (!SUPPORTED_ORDER.contains(order))
                throw OrderNotSupportedException(
                    message = OrderNotSupportedException.generateMessage(order)
                )
            else {
                builder.append("&order=$order")
            }
        }

        if (page != 1) builder.append("&page=$page")

        if (per_page != 20) {
            if (per_page !in 3..200)
                throw PerPageOutOfRangeException(
                    message = PerPageOutOfRangeException.generateMessage(per_page)
                )
            else {
                builder.append("&per_page=$per_page")
            }
        }

        if (callback.isNotEmpty()) builder.append("&callback=$callback")

        if (pretty) builder.append("&pretty=$pretty")

        return builder.toString()
    }

    override fun buildRequestQueryMap(): Map<String, String> {
        val queryMap: HashMap<String, String> = HashMap()

        queryMap["key"] = apiKey

        if (query.isNotEmpty()) {
            if (query.length > 100)
                throw QueryOverLimitException(
                    message = QueryOverLimitException.generateMessage(query.length)
                )
            else {
                queryMap["q"] = query
            }
        }

        if (lang != "en") {
            if (!SUPPORTED_LANGUAGES.contains(lang))
                throw LanguageNotSupportedException(
                    message = LanguageNotSupportedException.generateMessage(lang)
                )
            else {
                queryMap["lang"] = lang
            }
        }

        if (id.isNotEmpty()) queryMap["id"] = id

        if (image_type != "all") {
            if (!SUPPORTED_IMAGE_TYPES.contains(image_type))
                throw ImageTypeNotSupportedException(
                    message = ImageTypeNotSupportedException.generateMessage(image_type)
                )
            else {
                queryMap["image_type"] = image_type
            }
        }

        if (orientation != "all") {
            if (!SUPPORTED_IMAGE_ORIENTATIONS.contains(orientation))
                throw ImageOrientationNotSupportedException(
                    message = ImageOrientationNotSupportedException.generateMessage(orientation)
                )
            else {
                queryMap["orientation"] = orientation
            }
        }

        if (category.isNotEmpty()) {
            if (!SUPPORTED_CATEGORIES.contains(category))
                throw CategoryNotSupportedException(
                    message = CategoryNotSupportedException.generateMessage(category)
                )
            else {
                queryMap["category"] = category
            }
        }

        if (min_width != 0) queryMap["min_width"] = min_width.toString()

        if (min_height != 0) queryMap["min_width"] = min_height.toString()

        if (colors.isNotEmpty()) {
            if (!SUPPORTED_COLORS.contains(colors))
                throw ColorNotSupportedException(
                    message = ColorNotSupportedException.generateMessage(colors)
                )
            else {
                queryMap["category"] = colors
            }
        }

        if (editors_choice) queryMap["editors_choice"] = editors_choice.toString()

        if (safeSearch) queryMap["safesearch"] = safeSearch.toString()

        if (order != "popular") {
            if (!SUPPORTED_ORDER.contains(order))
                throw OrderNotSupportedException(
                    message = OrderNotSupportedException.generateMessage(order)
                )
            else {
                queryMap["order"] = order
            }
        }

        if (page != 1) queryMap["page"] = page.toString()

        if (per_page != 20) {
            if (per_page !in 3..200)
                throw PerPageOutOfRangeException(
                    message = PerPageOutOfRangeException.generateMessage(per_page)
                )
            else {
                queryMap["per_page"] = per_page.toString()
            }
        }

        if (callback.isNotEmpty()) queryMap["callback"] = callback

        if (pretty) queryMap["pretty"] = pretty.toString()

        return queryMap
    }

    override fun getResponseClassType(): Class<ImageSearchResult> {
        return ImageSearchResult::class.java
    }

}