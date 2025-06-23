package com.example.effectivemobilepracticaltasks

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.effectivemobilepracticaltasks.databinding.ActivityMainBinding
import com.example.effectivemobilepracticaltasks.topic_1_kotlin.findFirstInt
import com.example.effectivemobilepracticaltasks.topic_1_kotlin.prepareMixedList
import com.example.effectivemobilepracticaltasks.topic_1_kotlin.shakerSortWithNulls
import com.example.effectivemobilepracticaltasks.topoc_2_coroutines.throttleFirst
import com.example.effectivemobilepracticaltasks.topoc_2_coroutines.throttleLatest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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

        throttleLatestTest()
        throttleFirstTest()
    }

    fun throttleLatestTest() {
        GlobalScope.launch(Dispatchers.IO) {
            flow {
                emit("1")
                delay(100)
                emit("2")
                delay(100)
                emit("2,1")
                //следующее значение по идее должно проходить как последнее, тк укладывается в 300мс
                //но оно проходит только если вместо delay 100 поставить 90-95мс,
                // как это победить с ходу не придумал, но возможно так и дожно работать?
                delay(100)
                emit("2,2")
                delay(100)
                emit("3")
                delay(300)
                emit("4")
                delay(100)
                emit("5")
                delay(100)
                emit("5,1")
            }
                .throttleLatest(300)
                .collect { Log.d("throttleLatestTest", "throttleLatest: $it") }
        }
    }

    fun throttleFirstTest() {
        GlobalScope.launch(Dispatchers.IO) {
            flow {
                emit("1")
                delay(100)
                emit("2")
                delay(100)
                emit("2,1")
                delay(100)
                emit("2,2")
                delay(100)
                emit("3")
                delay(300)
                emit("4")
                delay(100)
                emit("5")
                delay(100)
                emit("5,1")
            }
                .throttleFirst(300)
                .collect { Log.d("throttleFirstTest", "throttleFirst: $it") }
        }
    }
}

