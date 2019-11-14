package com.fizus.events.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.fizus.events.R
import com.fizus.events.base.BaseFragment
import com.fizus.events.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_frontpage.*

class FrontpageFragment : BaseFragment() {
    override val layout: Int
        get() = R.layout.fragment_frontpage
    override val viewModel: BaseViewModel?
        get() = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
            findNavController().navigate(R.id.loginFragment, null)
        }

        btn_register.setOnClickListener {
            findNavController().navigate(R.id.registerFragment, null)
        }
    }
}