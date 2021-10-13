package com.kalevet.pixaget.data.models.video

/** This class olds the different video's sizes Pixabay has to ofer.
 *  see also @see VideoResolutionDetails
 *
 * @param   large   usually has a dimension of 1920x1080. If a large video version is not available, an empty URL value and a size of zero is returned.
 * @param   medium  typically has a dimension of 1280x720 and is available for all Pixabay videos.
 * @param   small   typically has a dimension of 960x540, older videos have a dimension of 640x360. This size is available for all videos.
 * @param   tiny    typically has a dimension of 640x360, older videos have a dimension of 480x270. This size is available for all videos.
 */
data class VideoResolution(
    val large: VideoResolutionDetails,
    val medium: VideoResolutionDetails,
    val small: VideoResolutionDetails,
    val tiny: VideoResolutionDetails
)