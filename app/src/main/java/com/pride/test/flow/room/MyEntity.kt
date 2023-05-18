package com.pride.test.flow.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TestTable")
data class MyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val stringgMane:String = "default"
)