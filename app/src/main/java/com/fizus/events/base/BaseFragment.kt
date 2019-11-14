package com.fizus.events.base

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fizus.events.MainActivity
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    protected val TAG = BaseFragment::class.java.simpleName
    protected val sharedPreferences by inject<SharedPreferences>()
    protected abstract val layout: Int
    protected abstract val viewModel: BaseViewModel?
    protected var loadingView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingView = getMainActivity()?.getLoadingView()
        viewModel?.onLoading()?.observe(this, loadingObserver())
        viewModel?.onError()?.observe(this, errorObserver())
    }

    open fun errorObserver(): Observer<Map<Int, String>> {
        return Observer {
            for ((key, value) in it) {
                if (key == 0) {
                    Toast.makeText(activity, value, Toast.LENGTH_SHORT).show()
                } else {
                    val view = view?.findViewById<EditText>(key)
                    view?.error = value
                }
            }
        }
    }

    open fun loadingObserver(): Observer<Boolean> {
        return Observer {
            Log.e(TAG, "Loading: $it")
            if (it)
                loadingView?.visibility = View.VISIBLE
            else
                loadingView?.visibility = View.GONE
        }
    }

    fun getMainActivity(): MainActivity? {
        return activity as MainActivity?
    }
}