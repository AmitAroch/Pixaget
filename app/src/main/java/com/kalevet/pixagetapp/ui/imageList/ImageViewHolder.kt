package com.kalevet.pixagetapp.ui.imageList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kalevet.pixagetapp.R
import com.kalevet.pixagetapp.data.models.image.ImageItemCash

private const val TITLE_DEFAULT = "LOADING"
private const val SUB_TITLE_DEFAULT = "LOADING"

class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    val title: TextView = itemView.findViewById(R.id.image_title)
    val subTitle: TextView = itemView.findViewById(R.id.image_sub_title)
    val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail_imageview)
    val userImage: ImageView = itemView.findViewById(R.id.user_image)


    fun bind(item: ImageItemCash?) {
        if (item == null) {
            title.text = TITLE_DEFAULT
            subTitle.text = SUB_TITLE_DEFAULT
        } else {
            title.text = item.title
            subTitle.text = item.subTitle
                /*item.tags
            ("${item.user} · " +
                    "${item.views} views · " +
                    "${item.likes} likes · " +
                    "${item.downloads} downloads")
                .also { subTitle.text = it }*/

            Glide.with(thumbnail.context)
                .load(item.webformatURL)
                //.apply(RequestOptions().override(400, 600))
                .thumbnail(Glide.with(thumbnail.context).load(item.previewURL))
                .error(R.drawable.no_video_image)
                .placeholder(R.drawable.no_video_image)
                .into(thumbnail)


            Glide.with(userImage.context)
                .load(item.userImageURL)
                .error(R.drawable.no_video_image)
                .placeholder(R.drawable.no_user_image)
                .into(userImage)
        }
    }

}