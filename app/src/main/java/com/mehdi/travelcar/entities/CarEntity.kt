package com.mehdi.travelcar.entities

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable


/**
 * Created by mehdi on 2020-02-15.
 */

class ListStringConverter {

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}

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
    @field:SerializedName("equipments")
    @TypeConverters(ListStringConverter::class) @ColumnInfo(name = "equipments") var equipments: List<String>? = null
}