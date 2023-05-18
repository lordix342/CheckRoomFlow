package com.pride.test.flow.repo

import com.pride.test.flow.room.MyEntity
import kotlinx.coroutines.flow.Flow

interface RepoImpl {
    suspend fun insert(new: MyEntity)
    fun getter() : Flow<List<MyEntity>>
}