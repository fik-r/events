package com.fizus.events.fragment.content.events

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.fizus.events.R
import com.fizus.events.adapter.EventsAdapter
import com.fizus.events.base.BaseFragment
import com.fizus.events.fragment.content.home.HomeFragmentDirections
import com.fizus.events.model.Event
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.shimmer_event.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class EventsFragment : BaseFragment() {
    companion object {
        private const val TYPE = "type"

        fun newInstance(type: String) = EventsFragment().apply {
            arguments = bundleOf(TYPE to type)
        }
    }

    override val layout: Int = R.layout.fragment_events
    override val viewModel: EventsViewModel by viewModel()

    private var type: String? = null
    private lateinit var eventsAdapter: EventsAdapter
    private val events: MutableList<Event> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEventList()

        type = arguments?.getString(TYPE, "")
        type?.let {
            viewModel.onLoadEvents().observe(this, eventsObserver())
            if (events.isNullOrEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.loadEvents(it)
                }
            }

        }
    }

    private fun setupEventList() {
        eventsAdapter = EventsAdapter(events, eventListener())
        rv_events.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_events.adapter = eventsAdapter
    }


    private fun eventListener(): EventsAdapter.Listener {
        return object : EventsAdapter.Listener {
            override fun onClick(position: Int) {
                val navController = findNavController()
                navController.navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailEventFragment(
                        events[position]
                    )
                )
            }
        }
    }

    private fun eventsObserver(): Observer<List<Event>> {
        return Observer {
            events.clear()
            events.addAll(it)
            eventsAdapter.notifyDataSetChanged()
        }
    }

    override fun loadingObserver(): Observer<Boolean> {
        return Observer {
            if (it) {
                rv_events.visibility = View.GONE
                loading.visibility = View.VISIBLE
                (loading as ShimmerFrameLayout).startShimmer()
            } else {
                (loading as ShimmerFrameLayout).stopShimmer()
                loading.visibility = View.GONE
                rv_events.visibility = View.VISIBLE
            }
        }
    }
}