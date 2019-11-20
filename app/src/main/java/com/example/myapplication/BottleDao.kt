package com.example.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BottleDao {

    @Query("SELECT * FROM bottle")
    fun getAllBottles():List<Bottle>

    @Insert
    fun insertBottle(vararg bottles: Bottle)

    @Delete
    fun deleteBottle(vararg bottles: Bottle)
}