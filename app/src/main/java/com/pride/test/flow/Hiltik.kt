package com.pride.test.flow

import android.content.Context
import com.pride.test.flow.repo.RepoImpl
import com.pride.test.flow.repo.Repository
import com.pride.test.flow.room.Dao
import com.pride.test.flow.room.MyRoomik
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Hiltik {

    @Provides
    @Singleton
    fun providedRoom(@ApplicationContext applicationContext: Context) : MyRoomik {
        return MyRoomik.getInstance(applicationContext)
    }

    @Provides
    @Singleton
    fun provideDao(db: MyRoomik) : Dao {
        return db.myDao()
    }


    @Provides
    @Singleton
    fun provideRepo(dao:Dao) : RepoImpl {
        return Repository(dao)
    }
}