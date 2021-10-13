package com.kalevet.pixaget.data.models.image

/**
 *
 * @param   id        A unique identifier for this image.
 * @param   pageURL    Source page on Pixabay, which provides a download link for the original image of the
 *                  dimension imageWidth x imageHeight and the file size imageSize.
 * @param   type    The type of the image: "photo", "illustration", "vector".
 * @param   tags    Tags related to the image Example: "flowers, yellow, blossom".
 * @param   previewURL    Low resolution images with a maximum width or height of 150 px (previewWidth x previewHeight).
 * @param   previewWidth    The width of the preview image.
 * @param   previewHeight   The height of the preview image.
 * @param   webformatURL  Medium sized image with a maximum width or height of 640 px (webformatWidth x webformatHeight).
 *                      URL valid for 24 hours.
 *                      Replace '_640' in any webformatURL value to access other image sizes:
 *                      Replace with '_180' or '_340' to get a 180 or 340 px tall version of the image, respectively.
 *                      Replace with '_960' to get the image in a maximum dimension of 960 x 720 px.
 * @param   webformatWidth  The width of the web format image.
 * @param   webformatHeight The height of the web format image.
 * @param   largeImageURL Scaled image with a maximum width/height of 1280px.
 * @param   fullHDURL   Full HD scaled image with a maximum width/height of 1920px. (This field will only available if your account has
 *                      been <a href="https://pixabay.com/api/request_full_access/">approved for full API access</a>)
 * @param   imageURL    URL to the original image (imageWidth x imageHeight).  (Required full API access)
 * @param   imageWidth  The width of the full image. (Required full API access)
 * @param   imageHeight The height of the full image. (Required full API access)
 * @param   imageSize   The size of the full image in bytes. (Required full API access)
 * @param   vectorURL   URL to a vector resource if available, else omitted. (Required full API access)
 * @param   views        Total number of views.
 * @param   downloads    Total number of downloads.
 * @param   likes        Total number of likes.
 * @param   comments    Total number of comments.
 * @param   user_id,    User ID of the contributor. Profile URL: https://pixabay.com/users/{ USERNAME }-{ ID }/
 * @param   user        Username of the contributor. Profile URL: https://pixabay.com/users/{ USERNAME }-{ ID }/
 * @param   userImageURL Profile picture URL (250 x 250 px).
 *
 */
data class ImageItem(
    val id: Int,
    val pageURL: String?,
    val type: String?,
    val tags: String?,
    val previewURL: String?,
    val previewWidth: Int?,
    val previewHeight: Int?,
    val webformatURL: String?,
    val webformatWidth: Int?,
    val webformatHeight: Int?,
    val largeImageURL: String?,
    val fullHDURL: String?,
    val imageURL: String?,
    val imageWidth: Int?,
    val imageHeight: Int?,
    val imageSize: Int?,
    val vectorURL: String?,
    val views: Int?,
    val downloads: Int?,
    val likes: Int?,
    val comments: Int?,
    val user_id: Int?,
    val user: String?,
    val userImageURL: String?
)

