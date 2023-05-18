package com.pride.test.flow.room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM TestTable")
    fun getAll() : Flow<List<MyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNew(newBalance: MyEntity)
}