package com.example.effectivemobilepracticaltasks

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.effectivemobilepracticaltasks.databinding.ActivityMainBinding
import com.example.effectivemobilepracticaltasks.topic_1_kotlin.findFirstInt
import com.example.effectivemobilepracticaltasks.topic_1_kotlin.prepareMixedList
import com.example.effectivemobilepracticaltasks.topic_1_kotlin.shakerSortWithNulls
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mixedList = prepareMixedList()
        val list = listOf(5, null, 1, null, 4, 2, 8, 0)

        binding.findInt.setOnClickListener {
            val firstInt = mixedList.findFirstInt()
            Log.d("FirstInt", "First Int in List<Any?>: $firstInt")
        }

        binding.shakerSort.setOnClickListener {
            val sorted = shakerSortWithNulls(list)
            Log.d("SortedList", "Sorted List<Int?>: $sorted")
        }
    }
}



