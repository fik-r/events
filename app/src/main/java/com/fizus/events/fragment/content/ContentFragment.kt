package com.fizus.events.fragment.content

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.fizus.events.R
import com.fizus.events.base.BaseFragment
import com.fizus.events.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_content.*

class ContentFragment : BaseFragment() {
    override val viewModel: BaseViewModel? = null
    override val layout: Int = R.layout.fragment_content

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navController =
            activity?.let { Navigation.findNavController(it, R.id.content_nav_host_fragment) }

        navController?.let { _navController ->
            bottom_nav.setupWithNavController(_navController)
            bottom_nav.setOnNavigationItemSelectedListener {
                NavigationUI.onNavDestinationSelected(it, _navController)
            }
            _navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.detailEventFragment) {
                    bottom_nav.visibility = View.GONE
                } else {
                    bottom_nav.visibility = View.VISIBLE
                }
            }
        }
    }
}