package com.app.movies.base

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Implementation of [Scheduler] with actual threads.
 * */
class AppScheduler : Scheduler {
  override fun computation(): io.reactivex.Scheduler = Schedulers.computation()

  override fun mainThread(): io.reactivex.Scheduler = AndroidSchedulers.mainThread()

  override fun io(): io.reactivex.Scheduler = Schedulers.io()
}