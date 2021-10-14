package com.kalevet.pixagetapp.data.models.image

import androidx.recyclerview.widget.DiffUtil

object ImageItemComparator : DiffUtil.ItemCallback<ImageItemCash>() {
    override fun areItemsTheSame(oldDetails: ImageItemCash, newDetails: ImageItemCash): Boolean {
        // Id is unique.
        return oldDetails.id == newDetails.id
    }

    override fun areContentsTheSame(oldDetails: ImageItemCash, newDetails: ImageItemCash): Boolean {
        return oldDetails == newDetails
    }
}