package com.pride.test.flow.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pride.test.flow.repo.RepoImpl
import com.pride.test.flow.room.MyEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: RepoImpl): ViewModel() {
    val listOfNames : LiveData<List<MyEntity>> = repo.getter().asLiveData(context = viewModelScope.coroutineContext + Dispatchers.IO)
    fun insert(newEnt:MyEntity) = CoroutineScope(Dispatchers.IO).launch {
        repo.insert(newEnt)
    }
}