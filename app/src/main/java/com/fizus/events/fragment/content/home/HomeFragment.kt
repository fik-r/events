package com.fizus.events.fragment.content.home

import android.os.Bundle
import android.view.View
import com.fizus.events.R
import com.fizus.events.adapter.EventsPagerAdapter
import com.fizus.events.base.BaseFragment
import com.fizus.events.base.BaseViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    override val viewModel: BaseViewModel? = null
    override val layout: Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventsPager()

    }

    private fun setupEventsPager() {
        vp_events.adapter = EventsPagerAdapter(childFragmentManager)
        vp_events.currentItem = 0
        vp_events.offscreenPageLimit = 3
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(vp_events))
        vp_events.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
    }
}