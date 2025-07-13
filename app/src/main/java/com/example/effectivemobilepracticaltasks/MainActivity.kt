package com.example.effectivemobilepracticaltasks

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobilepracticaltasks.databinding.ActivityMainBinding
import com.example.effectivemobilepracticaltasks.topic_3_rxjava.ItemAdapter
import com.example.effectivemobilepracticaltasks.topic_3_rxjava.Subject
import com.example.effectivemobilepracticaltasks.topic_3_rxjava.getServer1Cards
import com.example.effectivemobilepracticaltasks.topic_3_rxjava.getServer2Cards
import com.example.effectivemobilepracticaltasks.topic_3_rxjava.networkCall
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val disposables = CompositeDisposable()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNetworkContent()
        startTimer()
        setRv()
        setupEditTextDebounce()
        loadCardsVariantA()
        loadCardsVariantB()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    fun getNetworkContent() {
        val disposableNetworkCall = networkCall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                binding.networkContent.text = "Загрузка..."
            }
            .subscribe(
                { result ->
                    binding.networkContent.text = result
                },
                { error ->
                    binding.networkContent.text = "Ошибка: ${error.message}"
                }
            )

        disposables.add(disposableNetworkCall)
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    fun startTimer() {
        val disposableTimer = Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { seconds ->
                binding.timer.text = "Прошло секунд: $seconds"
            }
        disposables.add(disposableTimer)
    }

    fun setRv() {
        recyclerView = binding.rv
        recyclerView.adapter = ItemAdapter(listOf("Item 0", "Item 1", "Item 2", "Item 3", "Item 4"))

        val disposableRv = Subject.itemClickSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { position ->
                Toast.makeText(
                    this,
                    "Выбран элемент №$position",
                    Toast.LENGTH_SHORT
                ).show()
            }
        disposables.add(disposableRv)
    }

    @SuppressLint("CheckResult")
    fun setupEditTextDebounce() {
        val disposableEt = binding.editText.textChanges()
            .skipInitialValue()
            .debounce(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                Log.d("EditTextDebounce", "Введенный текст: $text")
            }
        disposables.add(disposableEt)
    }

    fun loadCardsVariantA() {
        val disposableLoadCardsVariantA = Observable.mergeDelayError(
            getServer1Cards().onErrorResumeNext { _: Throwable -> Observable.empty() },
            getServer2Cards(shouldFail = true).onErrorResumeNext { _: Throwable -> Observable.empty() }
        )
            .toList()
            .toObservable()
            .map { lists -> lists.flatten() }
            .subscribe(
                { cards ->
                    Log.d("loadCards", "Успех: $cards")
                },
                { error ->
                    Log.d("loadCards", "Ошибка: $error")
                }
            )
        disposables.add(disposableLoadCardsVariantA)
    }

    fun loadCardsVariantB() {
        val disposableLoadCardsVariantB = Observable.zip(
            getServer1Cards(),
            getServer2Cards(shouldFail = true),
            { cards1, cards2 -> cards1 + cards2 }
        )
            .subscribe(
                { cards ->
                    Log.d("loadCards", "Успех: $cards")
                },
                { error ->
                    Log.d("loadCards", "Ошибка: $error")
                }
            )
        disposables.add(disposableLoadCardsVariantB)
    }
}

