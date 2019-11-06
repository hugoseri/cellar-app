package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable


class MainActivity : AppCompatActivity(), BottleCreationFragment.OnFragmentInteractionListener, BottleListFragment.OnFragmentInteractionListener {

    private val cellar = Cellar("My Cellar", "0")
    lateinit var recyclerView: RecyclerView

    lateinit var fragmentAddBottle: BottleCreationFragment
    lateinit var fragmentListBottle: BottleListFragment

    lateinit var fragmentTransaction: FragmentTransaction

    override fun addBottle(bottle: Bottle) {
        cellar.addBottleStart(bottle)
        toastMessage("The bottle : " + cellar.getFirstBottle().name + " has been added to the cellar.")
        fragmentListBottle.updateRecyclerView()
    }

    override fun deleteBottle(index: Int) {
        var bottleDeleted = cellar.getBottle(index).name
        cellar.removeBottle(index)
        toastMessage("The bottle : $bottleDeleted has been removed from the cellar.")
        fragmentListBottle.updateRecyclerView()
    }

    override fun goToAddBottleFragment() {
        fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentAddBottle = BottleCreationFragment()
        fragmentTransaction.replace(R.id.a_main_rootview, fragmentAddBottle)

        fragmentTransaction.commit()
    }

    override fun goToListFragment() {
        fragmentTransaction = supportFragmentManager.beginTransaction()

        val bundle = Bundle()
        bundle.putSerializable(SEND_CELLAR_KEY, cellar.getBottles() as Serializable)
        fragmentListBottle = BottleListFragment()
        fragmentListBottle.arguments = bundle
        fragmentTransaction.replace(R.id.a_main_rootview, fragmentListBottle)

        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToListFragment()

        /*
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

    */
    }

    fun toastMessage(message: String){
        val toast = Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        )
        toast.show()
    }
}
