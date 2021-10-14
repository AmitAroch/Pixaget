package com.kalevet.pixaget.data.repositories.remote.requests

import com.kalevet.pixaget.data.repositories.remote.requests.PixabaySearchRequest.*
import com.kalevet.pixaget.data.repositories.remote.responses.VideoSearchResult
import com.kalevet.pixaget.exceptions.PerPageOutOfRangeException
import com.kalevet.pixaget.exceptions.QueryOverLimitException
import com.kalevet.pixaget.utill.PIXABAY_BASE_URL

/** A class to specify a video search on the Pixaget's repository server using the @see <a href="https://pixabay.com/api/docs/">Pixaget API</a>
 * @param   query   A URL encoded search term. If omitted, all videos
 *                  are returned. This value may not exceed 100 characters.
 *                  Example: "yellow+flower"
 *  @param  lang    <a href="https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes">Language</a> code of the language to be searched in.
 *                  Accepted values: cs, da, de, en, es, fr, id, it, hu,
 *                  nl, no, pl, pt, ro, sk, fi, sv, tr, vi, th, bg, ru,
 *                  el, ja, ko, zh
 *                  Default: "en"
 *  @param  id  Retrieve individual videos by ID.
 *  @param  video_type  Filter results by video type.
 *                      Accepted values: "all", "film", "animation"
 *                      Default: "all"
 *  @param  category    Filter results by category.
 *                      Accepted values: backgrounds, fashion, nature, science, education, feelings, health, people, religion, places, animals, industry, computer, food, sports, transportation, travel, buildings, business, music
 *  @param  min_width   Minimum video width.
 *                      Default: "0"
 *  @param  min_height  Minimum video height.
 *                      Default: "0"
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
 *                  Accepted values: "        viewModelScope.launch(Dispatchers.IO) {
", "false"
 *                  Default: "false"
 *
 *  @return a VideoSearchRequest object to be used to create a request from the Pixaget server
 */
data class VideoSearchRequest @JvmOverloads constructor(
    val query: String = String(),
    val lang: Language = Language.English,
    val id: String = String(),
    val video_type: VideoType = VideoType.all,
    val category: Category = Category.all,
    val min_width: Int = 0,
    val min_height: Int = 0,
    val editors_choice: Boolean = false,
    val safeSearch: Boolean = false,
    val order: Order = Order.popular,
    var page: Int = 1,
    val per_page: Int = 20,
    val callback: String = String(),
    val pretty: Boolean = false
) : PixabaySearchRequest<VideoSearchResult> {

    override var apiKey: String = String()

    override fun buildRequestUrl(): String {
        val builder = StringBuilder()
        builder.append(PIXABAY_BASE_URL)
        builder.append("api/videos/")

        builder.append("?key=$this.apiKey")

        if (query.isNotEmpty()) {
            if (query.length > 100)
                throw QueryOverLimitException(
                    message = QueryOverLimitException.generateMessage(query.length)
                )
            else {
                builder.append("&q=$query")
            }
        }

        if (lang != Language.English) {
            builder.append("&lang=${lang.value}")
        }

        if (id.isNotEmpty()) {
            builder.append("&id=$id")
        }

        if (video_type != VideoType.all) {
                builder.append("&video_type=$video_type")
        }

        if (category != Category.all) {
                builder.append("&category=$category")
        }

        if (min_width != 0) {
            builder.append("&min_width=$min_width")
        }

        if (min_height != 0) {
            builder.append("&min_height=$min_height")
        }

        if (editors_choice) {
            builder.append("&editors_choice=$editors_choice")
        }

        if (safeSearch) {
            builder.append("&safesearch=$safeSearch")
        }

        if (order != Order.popular) {
                builder.append("&order=$order")
        }

        if (page != 1) {
            builder.append("&page=$page")
        }

        if (per_page != 20) {
            if (per_page !in 3..200)
                throw PerPageOutOfRangeException(
                    message = PerPageOutOfRangeException.generateMessage(per_page)
                )
            else {
                builder.append("&per_page=$per_page")
            }
        }

        if (callback.isNotEmpty()) {
            builder.append("&callback=$callback")

        }

        if (pretty) {
            builder.append("&pretty=$pretty")
        }

        return builder.toString()
    }

    override fun buildRequestQueryMap(): Map<String, String> {
        val queryMap: HashMap<String, String> = HashMap()

        queryMap["key"] = this.apiKey

        if (query.isNotEmpty()) {
            if (query.length > 100)
                throw QueryOverLimitException(
                    message = QueryOverLimitException.generateMessage(query.length)
                )
            else {
                queryMap["q"] = query
            }
        }

        if (lang != Language.English) {
                queryMap["lang"] = lang.value
        }

        if (id.isNotEmpty()) queryMap["id"] = id

        if (video_type != VideoType.all) {
                queryMap["image_type"] = video_type.name
        }

        if (category != Category.all) {
                queryMap["category"] = category.name
        }

        if (min_width != 0) queryMap["min_width"] = min_width.toString()

        if (min_height != 0) queryMap["min_width"] = min_height.toString()

        if (editors_choice) queryMap["editors_choice"] = editors_choice.toString()

        if (safeSearch) queryMap["safesearch"] = safeSearch.toString()

        if (order != Order.popular) {
                queryMap["order"] = order.name
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

    override fun getResponseClassType(): Class<VideoSearchResult> {
        return VideoSearchResult::class.java
    }

}