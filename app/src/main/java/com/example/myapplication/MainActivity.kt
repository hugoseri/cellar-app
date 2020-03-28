package com.example.myapplication

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
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

    fun deleteAllBottles(){
        cellar.removeAllBottles()
        toastMessage("All bottles have been removed from the cellar.")
        fragmentListBottle.updateRecyclerView()
    }

    override fun goToAddBottleFragment() {
        fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentAddBottle = BottleCreationFragment()
        fragmentTransaction.replace(R.id.a_main_rootview, fragmentAddBottle)

        fragmentTransaction.addToBackStack("add_bottle")

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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.m_delete_bottles-> {
                deleteAllBottles()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun toastMessage(message: String){
        val toast = Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0, 0)
        toast.show()
    }
}
