package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BottleDataAdapter(private val listBottles: ArrayList<Bottle>, private val deleteListener: BottleListFragment.OnFragmentInteractionListener?) :
    RecyclerView.Adapter<BottleDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottleDataViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.activity_row_bottle, parent, false)

        return BottleDataViewHolder(row)
    }

    override fun onBindViewHolder(viewholder: BottleDataViewHolder, position: Int) {
        val (name, price) = this.listBottles[position]

        viewholder.bottleName.text = name
        viewholder.bottlePrice.text = price.toString() + "€"

        viewholder.bottleDelete.setOnClickListener { view: View? ->
            deleteListener?.deleteBottleLocally(position)
        }
    }


    override fun getItemCount(): Int {
        return this.listBottles.size
    }
}