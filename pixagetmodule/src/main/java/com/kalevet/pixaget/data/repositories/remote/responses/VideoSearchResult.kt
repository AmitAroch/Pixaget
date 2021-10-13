package com.kalevet.pixaget.data.repositories.remote.responses

import com.kalevet.pixaget.data.models.video.VideoItem

/** This class represent a json response for a Pixabay video search.
 *  see also @see VideoItem
 *
 * @param total	The total number of hits.
 * @param totalHits	The number of videos accessible through the API. By default, the API is limited to
 *                  return a maximum of 500 videos per query.
 * @param hits  A list of the video search results
 */
data class VideoSearchResult(
    val total: Int,
    val totalHits: Int,
    var hits: List<VideoItem>
) : PixabaySearchResult
