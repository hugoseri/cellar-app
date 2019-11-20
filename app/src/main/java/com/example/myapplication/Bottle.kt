package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Bottle(val name: String,
                  val price: Double): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}