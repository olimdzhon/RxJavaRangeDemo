package com.example.rangedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var myObservable: Observable<Int>
    private lateinit var myObserver: DisposableObserver<Int>

    private lateinit var textView: TextView
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tvGreeting)
        myObservable = Observable.range(1,20)

        compositeDisposable.add(
            myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<Int> {

        myObserver = object : DisposableObserver<Int>() {

            override fun onNext(s: Int) {
                Log.d("MAN","onNext: $s")
                textView.text = s.toString()
            }

            override fun onError(e: Throwable) {
                Log.d("MAN","onError")
            }

            override fun onComplete() {
                Log.d("MAN","onComplete")
            }

        }

        return myObserver
    }
}