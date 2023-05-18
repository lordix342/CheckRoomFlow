package com.pride.test.flow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pride.test.flow.databinding.ActivityMainBinding
import com.pride.test.flow.room.MyEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainVM: MainViewModel by viewModels()
    private val adapter = MainAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mainVM.listOfNames.observe(this) {
            adapter.listItems = it
        }
        binding.button.setOnClickListener {
            mainVM.insert(MyEntity(null, binding.editName.text.toString()))
        }
        binding.rcName.adapter = adapter
        binding.rcName.layoutManager = LinearLayoutManager(this)
    }
}