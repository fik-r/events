package com.fizus.events.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.fizus.events.fragment.content.events.EventsFragment
import com.fizus.events.utility.Config

class EventsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        Log.i("position", "position: $position")
        return when (position) {
            0 -> EventsFragment.newInstance(Config.EVENTS_ALL)
            1 -> EventsFragment.newInstance(Config.EVENTS_UPCOMING)
            2 -> EventsFragment.newInstance(Config.EVENTS_FINISHED)
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_UNCHANGED
    }
}