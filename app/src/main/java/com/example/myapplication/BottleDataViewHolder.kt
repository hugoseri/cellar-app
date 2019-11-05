package com.example.myapplication

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BottleDataViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    var bottleIcon: ImageView

    var bottleNameDescr: TextView
    var bottleName: TextView

    var bottlePriceDescr: TextView
    var bottlePrice: TextView

    var bottleDelete: ImageButton

    init {
        this.bottleIcon = rootView.findViewById(R.id.a_row_bottle_icon)

        this.bottleNameDescr = rootView.findViewById(R.id.a_row_bottle_textview_bottle_name_descr)
        this.bottleName = rootView.findViewById(R.id.a_row_bottle_textview_bottle_name)

        this.bottlePriceDescr = rootView.findViewById(R.id.a_row_bottle_textview_bottle_price_descr)
        this.bottlePrice = rootView.findViewById(R.id.a_row_bottle_textview_bottle_price)

        this.bottleDelete= rootView.findViewById(R.id.a_row_bottle_delete)
    }
}