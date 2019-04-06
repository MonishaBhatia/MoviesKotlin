package com.app.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.tabs
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.viewPager

class HomeActivity : AppCompatActivity() {

  override fun onCreate(
    savedInstanceState: Bundle?
  ) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setSupportActionBar(toolbar)

    val adapter = ViewPagerAdapter(supportFragmentManager)
    adapter.addFragment(MoviesTabFragment(), "Movies")
    adapter.addFragment(FavTabFragment(), "Favourites")
    viewPager.adapter = adapter
    tabs.setupWithViewPager(viewPager)
  }
}
