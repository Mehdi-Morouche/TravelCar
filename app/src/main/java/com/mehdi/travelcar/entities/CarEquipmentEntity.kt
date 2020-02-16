package com.mehdi.travelcar.entities

import androidx.room.*

/**
 * Created by mehdi on 2020-02-16.
 */
@Entity data class CarEquipmentEntity (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val carId: Long,
    val equipmentId: Long
)

data class CarWithEquipments (
    @Embedded
    val equipment: EquipmentEntity,
    @Relation(
        parentColumn = "eId",
        entity = CarEntity::class,
        entityColumn = "cId",
        associateBy = Junction(
            value = CarEquipmentEntity::class,
            parentColumn = "carId",
            entityColumn = "equipmentId"
        )
    )
    val equipments: List<EquipmentEntity>
)