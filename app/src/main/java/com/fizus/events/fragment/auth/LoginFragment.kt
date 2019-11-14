package com.fizus.events.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fizus.events.R
import com.fizus.events.base.BaseFragment
import com.fizus.events.utility.Config
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    override val layout: Int
        get() = R.layout.fragment_login

    override val viewModel: LoginViewModel
        get() = viewModel<LoginViewModel>().value

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setupWithNavController(findNavController())

        //subscribe
        viewModel.isLoginSuccess().observe(this, loginObserver())

        btn_login.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.login(et_email.text.toString(), et_password.text.toString())
            }
        }
    }

    private fun loginObserver(): Observer<String> {
        return Observer {
            sharedPreferences.edit()
                .putString(Config.PREF_TOKEN, it)
                .apply()

            findNavController().navigate(R.id.contentFragment)
        }
    }


}