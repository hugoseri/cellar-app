package com.example.myapplication


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BottleListFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    lateinit var recyclerView: RecyclerView;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootview = inflater.inflate(R.layout.fragment_bottle_list, container, false)

        recyclerView = rootview.findViewById<RecyclerView>(R.id.a_main_list_bottles)
        val adapter = BottleDataAdapter(arguments!!.getSerializable(SEND_CELLAR_KEY) as ArrayList<Bottle>, listener)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        val buttonNewBottle = rootview.findViewById<FloatingActionButton>(R.id.a_main_btn_add_bottle)
        buttonNewBottle.setOnClickListener{ view: View? ->
            listener?.goToAddBottleFragment()
        }
        return rootview
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun deleteBottle(position: Int)
        fun goToAddBottleFragment()
    }

    fun updateRecyclerView(){
        recyclerView.adapter?.notifyDataSetChanged()
    }
}
