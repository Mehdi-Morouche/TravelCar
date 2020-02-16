package com.mehdi.travelcar.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Created by mehdi on 2020-02-15.
 */

@Serializable
@Entity(tableName = "car")
data class CarEntity(
    @PrimaryKey
    @field:SerializedName("set_num")
    val id: String? = null,
    @field:SerializedName("make")
    val make: String,
    @field:SerializedName("model")
    val model: String? = null,
    @field:SerializedName("year")
    val year: Int,
    @field:SerializedName("picture")
    val picture: String? = null,
    @field:SerializedName("equipments")
    val equipments: List<String>? = null
)