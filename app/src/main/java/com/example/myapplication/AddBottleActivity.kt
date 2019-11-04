package com.example.myapplication
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddBottleActivity : AppCompatActivity() {

    lateinit var bottle: Bottle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bottle)
        val btnAddBottle = findViewById<Button>(R.id.a_add_bottle_button_add)
        val bottleName = findViewById<EditText>(R.id.a_add_bottle_text_name)
        val bottlePrice = findViewById<EditText>(R.id.a_add_bottle_text_price)
        btnAddBottle.setOnClickListener{ view: View? ->
            if (!bottleName.text.toString().isEmpty() && !bottlePrice.text.toString().isEmpty()) {
                bottle = Bottle(bottleName.text.toString(), bottlePrice.text.toString().toInt())
                stopActivityAndReturnResult()
            } else {
                val toast = Toast.makeText(
                    applicationContext,
                    "The fields must be fullfiled.",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }
    fun stopActivityAndReturnResult(){
        val returnIntent = Intent()
        returnIntent.putExtra(CREATE_BOTTLE_EXTRA_KEY, bottle)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
