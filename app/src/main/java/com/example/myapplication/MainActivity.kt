package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddBottle = findViewById<Button>(R.id.a_main_btn_add_bottle)
        btnAddBottle.setOnClickListener{ view: View? ->
            var myBottle: Bottle = Bottle("Default name", 10)
            Toast.makeText(this@MainActivity, "Bottle created : ${myBottle.name} - ${myBottle.price}€", Toast.LENGTH_SHORT).show()
        }
    }
}
