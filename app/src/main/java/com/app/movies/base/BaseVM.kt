package com.app.movies.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseVM : ViewModel() {

  val disposable = CompositeDisposable()

  val loading: MutableLiveData<Boolean> by lazy {
    MutableLiveData<Boolean>()
  }

  val error: MutableLiveData<Throwable> by lazy {
    MutableLiveData<Throwable>()
  }

  fun cleanUrl(url: String) =
    if (url.isNotEmpty() && url.first() == '/') url.replaceFirst("/", "") else url

  protected fun handleError(err: Throwable) {
    loading.postValue(false)
    error.postValue(err)
  }

  override fun onCleared() {
    super.onCleared()
    disposable.dispose()
  }

  fun errorHandled() {
    error.value = null
  }

}