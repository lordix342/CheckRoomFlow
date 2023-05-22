package com.pride.test.flow.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [MyEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class MyRoomik : RoomDatabase() {
    abstract fun myDao() : Dao
    companion object {
        @Volatile
        private var instance: MyRoomik? = null

        fun getInstance(context: Context): MyRoomik {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MyRoomik {
            return Room.databaseBuilder(
                context.applicationContext,
                MyRoomik::class.java,
                "my_database"
            ).build()
        }
    }
}