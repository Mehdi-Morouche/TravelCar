package com.mehdi.travelcar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by mehdi on 2020-02-16.
 */
@Entity(tableName = "account")
class AccountEntity {
    @PrimaryKey var id: Int = 0
    @ColumnInfo(name = "lastname") var lastname: String? = null
    @ColumnInfo(name = "name") var name: String? = null
    @ColumnInfo(name = "address") var address: String? = null
    @ColumnInfo(name = "birthdate") var birthdate: String? = null
    @ColumnInfo(name = "photo") var photo: String? = null
}