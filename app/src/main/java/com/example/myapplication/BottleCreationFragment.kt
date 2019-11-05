package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class BottleCreationFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    lateinit var bottle: Bottle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_bottle_creation, container, false)

        val btnAddBottle = rootView.findViewById<Button>(R.id.a_add_bottle_button_add)
        val bottleName = rootView.findViewById<EditText>(R.id.a_add_bottle_text_name)
        val bottlePrice = rootView.findViewById<EditText>(R.id.a_add_bottle_text_price)

        btnAddBottle.setOnClickListener { view: View? ->
            if (!bottleName.text.toString().isEmpty() && !bottlePrice.text.toString().isEmpty()) {
                bottle = Bottle(bottleName.text.toString(), bottlePrice.text.toString().toInt())
                listener?.addBottle(bottle)
                listener?.goToListFragment()
            } else {
                val toast = Toast.makeText(
                    activity,
                    "The fields must be fullfiled.",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
        return rootView
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
        fun addBottle(bottle: Bottle)
        fun goToListFragment()
    }
}
