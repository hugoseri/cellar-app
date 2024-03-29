package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BottleDataAdapter(private val cellar: Cellar, private val deleteListener: onDeleteListener) :
    RecyclerView.Adapter<BottleDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottleDataViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.activity_row_bottle, parent, false)

        return BottleDataViewHolder(row)
    }

    override fun onBindViewHolder(viewholder: BottleDataViewHolder, position: Int) {
        val (name, price) = this.cellar.getBottle(position)

        viewholder.bottleName.text = name
        viewholder.bottlePrice.text = price.toString()

        viewholder.bottleDelete.setOnClickListener { view: View? ->
            deleteListener.deleteBottle(position)
        }
    }



    override fun getItemCount(): Int {
        return this.cellar.getNumberOfBottles()
    }
}