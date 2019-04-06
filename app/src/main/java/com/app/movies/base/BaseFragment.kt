package com.app.movies.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders


abstract class BaseFragment : Fragment() {

  private val vmFactory: ViewModelProvider.Factory by lazy { provideVMFactory() }

  protected val vm: BaseVM by lazy { provideVM() }

  abstract val vmClass: Class<out BaseVM>

  abstract val layout: Int

  abstract fun provideVMFactory(): Factory

  protected open fun provideVM() = ViewModelProviders.of(this, vmFactory).get(vmClass)

}