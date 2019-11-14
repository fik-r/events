package com.fizus.events.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fizus.events.R
import com.fizus.events.base.BaseFragment
import com.fizus.events.utility.Config
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment() {

    override val layout: Int
        get() = R.layout.fragment_register
    override val viewModel: RegisterViewModel
        get() = viewModel<RegisterViewModel>().value

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //code here

        toolbar.setupWithNavController(findNavController())
        //subscribe
        viewModel.isRegisterSuccess().observe(this, registerObserver())

        btn_register.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.register(
                    et_name.text.toString(),
                    et_email.text.toString(),
                    et_password.text.toString()
                )
            }
        }
    }

    private fun registerObserver(): Observer<String> {
        return Observer {
            sharedPreferences.edit()
                .putString(Config.PREF_TOKEN, it)
                .apply()

            findNavController().navigate(R.id.contentFragment)
        }
    }

}