package com.awesome.amumanager.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.data.model.Promotion
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_promotion.view.*


class PromotionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val storeName = itemView.store_name
    private val promotionDate = itemView.date
    private val promotionMessage = itemView.message

    fun bind(promotion : Promotion , requestManager : RequestManager) {
        storeName.text = promotion.store_name
        promotionDate.text = promotion.date
        promotionMessage.text = promotion.message
    }
}