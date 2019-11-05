package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), onDeleteListener {

    private val cellar = Cellar("My Cellar", "0")
    lateinit var recyclerView: RecyclerView

    override fun deleteBottle(index: Int) {
        cellar.removeBottle(index)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //gestion liste bouteilles affichées
        recyclerView = findViewById<RecyclerView>(R.id.a_main_list_bottles)
        val adapter = BottleDataAdapter(cellar, this)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        //bouton ajout bouteille
        val btnAddBottle = findViewById<Button>(R.id.a_main_btn_add_bottle)
        btnAddBottle.setOnClickListener { view: View? ->
            val addBottleIntent = Intent(this, AddBottleActivity::class.java)
            startActivityForResult(addBottleIntent, CREATE_BOTTLE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_BOTTLE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            val bottle: Bottle = data.getSerializableExtra(CREATE_BOTTLE_EXTRA_KEY) as Bottle
             cellar.addBottleStart(bottle)

            val toast = Toast.makeText(
                applicationContext,
                "The bottle : " + cellar.getFirstBottle().name + " has been added to the cellar.",
                Toast.LENGTH_SHORT
            )
            toast.show()

            recyclerView.adapter?.notifyDataSetChanged()
        }
    }
}
