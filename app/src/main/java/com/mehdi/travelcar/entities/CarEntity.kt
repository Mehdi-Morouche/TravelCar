package com.mehdi.travelcar.entities

import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Created by mehdi on 2020-02-15.
 */

@Serializable
@Entity(tableName = "car", indices = arrayOf(Index(value = ["make", "model"], unique = true)))
class CarEntity {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @field:SerializedName("make")
    @ColumnInfo(name = "make") var make: String? = null
    @field:SerializedName("model")
    @ColumnInfo(name = "model") var model: String? = null
    @field:SerializedName("year")
    @ColumnInfo(name = "year") var year: Int? = null
    @field:SerializedName("picture")
    @ColumnInfo(name = "picture") var picture: String? = null
    //@field:SerializedName("equipments")
    //@ColumnInfo(name = "equipments")
    //val equipments: List<String>? = null
}