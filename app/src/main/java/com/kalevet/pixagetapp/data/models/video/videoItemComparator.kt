package com.kalevet.pixagetapp.data.models.video

import androidx.recyclerview.widget.DiffUtil

object videoItemComparator : DiffUtil.ItemCallback<VideoItemCash>() {
    override fun areItemsTheSame(oldDetails: VideoItemCash, newDetails: VideoItemCash): Boolean {
        // Id is unique.
        return oldDetails.id == newDetails.id
    }

    override fun areContentsTheSame(oldDetails: VideoItemCash, newDetails: VideoItemCash): Boolean {
        return oldDetails == newDetails
    }
}