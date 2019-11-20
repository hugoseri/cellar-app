package com.example.myapplication

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class MainActivity : AppCompatActivity(), BottleCreationFragment.OnFragmentInteractionListener, BottleListFragment.OnFragmentInteractionListener {

    private val cellar = Cellar("My Cellar", "0")

    lateinit var fragmentAddBottle: BottleCreationFragment
    lateinit var fragmentListBottle: BottleListFragment

    lateinit var fragmentTransaction: FragmentTransaction

    lateinit var requestQueue: RequestQueue

    lateinit var apiCalls: APICalls

    private lateinit var bottleDao: BottleDao

    var cellarId: String = "1"

    override fun addBottleInAPI(bottle: Bottle) {
        apiCalls.addBottle(cellarId, bottle)
            .enqueue(object : Callback<Bottle> {
                override fun onResponse(
                    call: Call<Bottle>,
                    response: retrofit2.Response<Bottle>) {
                    val bottle = response.body()
                    toastMessage("Bottle ${bottle?.name} has been added to the API.")
                }

                override fun onFailure(call: Call<Bottle>, t: Throwable) {
                    toastMessage("No bottle added in API.")
                }
            })
        addBottleInLocalDatabase(bottle)
    }

    fun addBottleInLocalDatabase(bottle: Bottle){
        toastMessage("Bottle added in local database.")
        bottleDao.insertBottle(bottle)
    }

    fun addBottleInView(bottle: Bottle){
        cellar.addBottleStart(bottle)
        toastMessage("The bottle : " + cellar.getFirstBottle().name + " has been added to the cellar.")
        fragmentListBottle.updateRecyclerView()
    }

    fun getAPIBottlesWithVolley(cellarId: Int){
        val stringRequest = StringRequest(
            Request.Method.GET,
            API_BASE_URL + "cellars/" + cellarId + "/bottles",
            Response.Listener<String> {
                val gson = GsonBuilder().create()
                val bottlesFromServer = gson.fromJson<Array<Bottle>>(it, Array<Bottle>::class.java)

                for (element in bottlesFromServer){
                    addBottleInAPI(element)
                }
            },
            Response.ErrorListener {
                toastMessage("It fails with error: $it")
            }
        )

        requestQueue.add(stringRequest)
    }

    fun getAllBottlesFromAPI(){
        apiCalls.getAllBottles(cellarId)
            .enqueue(object : Callback<ArrayList<Bottle>> {
                override fun onResponse(
                    call: Call<ArrayList<Bottle>>,
                    response: retrofit2.Response<ArrayList<Bottle>>) {
                    toastMessage("Get all bottles working.")
                    val bottles = response.body()
                    for (bottle in bottles.orEmpty()){
                        addBottleInView(bottle)
                    }
                    if (bottles.orEmpty().isEmpty()){
                        getAllBottlesFromLocal()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Bottle>>, t: Throwable) {
                    toastMessage("API request not working, saving locally.")
                    getAllBottlesFromLocal()
                }
            })
    }

    fun getAllBottlesFromLocal(){
        toastMessage("Get all local bottles.")
        val bottles = bottleDao.getAllBottles()
        for (bottle in bottles){
            addBottleInView(bottle)
        }
    }

    fun initRetrofit(){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_BASE_URL)
            .build()

        apiCalls = retrofit.create<APICalls>(APICalls::class.java)
    }

    override fun deleteBottleLocally(position: Int) {
        val bottleDeleted = cellar.getBottle(position)
        var bottleDeletedName = bottleDeleted.name

        bottleDao.deleteBottle(bottleDeleted)

        cellar.removeBottle(position)
        toastMessage("The bottle : $bottleDeletedName has been removed from the cellar (only locally).")
        fragmentListBottle.updateRecyclerView()
    }

    private fun deleteAllBottlesLocally(){
        cellar.removeAllBottles()
        bottleDao.deleteBottle(*cellar.getBottles().toTypedArray())
        toastMessage("All bottles have been removed from the cellar (only locally).")
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

        getAllBottlesFromAPI()

        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //requestQueue = Volley.newRequestQueue(this)

        bottleDao = AppDatabase.getAppDatabase(this).getBottleDao()

        initRetrofit()

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
                deleteAllBottlesLocally()
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
