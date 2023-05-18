package com.pride.test.flow.repo

import com.pride.test.flow.room.Dao
import com.pride.test.flow.room.MyEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val dao: Dao) : RepoImpl {
    override suspend fun insert(new: MyEntity) {
        dao.insertNew(new)
    }

    override fun getter(): Flow<List<MyEntity>> {
        return dao.getAll()
    }
}