package com.kalevet.pixagetapp.ui.imageList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kalevet.pixagetapp.R
import com.kalevet.pixagetapp.data.models.image.ImageItemCash


class ImageListAdapter(diffCallback: DiffUtil.ItemCallback<ImageItemCash>)
    : PagingDataAdapter<ImageItemCash, ImageViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        holder.bind(item)
    }

}