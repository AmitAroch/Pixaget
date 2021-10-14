package com.kalevet.pixaget.data.models.video


/** This class represent a json response for one video result of the Pixaget video search.
 * see also @see VideoResolution and @see VideoResolutionDetails
 *
 * @param id    A unique identifier for this video.
 * @param pageURL    Source page on Pixaget.
 * @param type  The type of the video: "film" or "animation".
 * @param tags  Tags related to the video Example: "flowers, yellow, blossom".
 * @param duration  The duration of the video in seconds.
 * @param picture_id    This value may be used to retrieve static preview images of the video in various sizes:
 *                      https://i.vimeocdn.com/video/{ PICTURE_ID }_{ SIZE }.jpg
 *                      Available sizes: 100x75, 200x150, 295x166, 640x360, 960x540, 1920x1080
 *                      Exampe: https://i.vimeocdn.com/video/529927645_295x166.jpg
 * @param pictureURL A URL of the video's page on the Pixaget website.
 * @param videos    A set of differently sizes video streams @see VideoResolution .
 *                  Append the GET parameter "download=1" to any of the video stream URLs to have them served as downloads.
 *
 * @param   views    Total number of views.
 * @param   downloads    Total number of downloads.
 * @param   likes    Total number of likes.
 * @param   comments    Total number of comments.
 * @param   user_id,    User ID of the contributor. Profile URL: https://pixabay.com/users/{ USERNAME }-{ ID }/
 * @param   user        Username of the contributor. Profile URL: https://pixabay.com/users/{ USERNAME }-{ ID }/
 * @param   userImageURL Profile picture URL (250 x 250 px).
 *
 */
data class VideoItem(
    val id: Int,
    val pageURL: String?,
    val type: String?,
    val tags: String?,
    val duration: Int?,
    val picture_id: String?,
    var pictureURL: String?,
    val videos: VideoResolution?,
    val views: Int?,
    val downloads: Int?,
    val likes: Int?,
    val comments: Int?,
    val user_id: Int?,
    val user: String?,
    val userImageURL: String?
)