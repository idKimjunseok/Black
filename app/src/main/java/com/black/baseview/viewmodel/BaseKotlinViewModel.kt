package com.black.baseview.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseKotlinViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun setOnCleared() {
        onCleared()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}