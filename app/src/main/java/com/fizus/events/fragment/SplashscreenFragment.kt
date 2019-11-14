package com.fizus.events.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.fizus.events.R
import com.fizus.events.base.BaseFragment
import com.fizus.events.base.BaseViewModel
import com.fizus.events.utility.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashscreenFragment : BaseFragment() {

    override val layout: Int = R.layout.fragment_splashscreen
    override val viewModel: BaseViewModel? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Default).launch {
            delay(1500L)
            fragmentManager?.let { _ ->
                val token = sharedPreferences.getString(Config.PREF_TOKEN, "")
                token?.let {
                    val destination = if (it.isEmpty()) {
                        R.id.action_splashscreenFragment_to_auth_nav_graph
                    } else
                        R.id.action_splashscreenFragment_to_contentFragment
                    findNavController().navigate(
                        destination,
                        null
                    )
                }
            }
        }
    }
}