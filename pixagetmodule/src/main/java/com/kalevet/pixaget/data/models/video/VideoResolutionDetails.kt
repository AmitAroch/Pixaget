package com.kalevet.pixaget.data.models.video

/** This class olds the single video's size dimensions and url.
 *  see also @see VideoResolutionDetails
 *
 * @param   url     A url to the video.
 * @param   width   The video width dimension in pixels.
 * @param   height  The video Height dimension in pixels.
 * @param   size    The size of the video in bytes.
 */
data class VideoResolutionDetails(
    val url: String,
    val width: Int,
    val height: Int,
    val size: Int
)