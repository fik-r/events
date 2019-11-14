package com.fizus.events.fragment.content.events.detail

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fizus.events.R
import com.fizus.events.adapter.PhotosAdapter
import com.fizus.events.base.BaseFragment
import com.fizus.events.base.BaseViewModel
import com.fizus.events.model.Event
import com.fizus.events.utility.Config
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.bottom_sheet_detail_event.*
import kotlinx.android.synthetic.main.fragment_detail_event.*


class DetailEventFragment : BaseFragment() {
    override val layout: Int
        get() = R.layout.fragment_detail_event
    override val viewModel: BaseViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation()

        toolbar.setupWithNavController(findNavController())

        val event = arguments?.let { DetailEventFragmentArgs.fromBundle(it).event }
        event?.let {
            if (event.photos.isNotEmpty()) {
                setupPhotos(event.photos)
            }
            bind(event)
        }

        val sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(v: View, float: Float) {
                btn_see_discussion?.alpha = float
            }

            override fun onStateChanged(v: View, state: Int) {
                when (state) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        btn_see_discussion.isClickable = true
                        val objAnimator =
                            ObjectAnimator.ofFloat(v as MaterialCardView, "radius", 0f)
                        objAnimator.duration = 80
                        objAnimator.doOnEnd {
                            v.clearAnimation()
                        }
                        objAnimator.start()
                    }
                    else -> {
                        btn_see_discussion.isClickable = false
                        val objAnimator = ObjectAnimator.ofFloat(
                            v as MaterialCardView,
                            "radius",
                            resources.getDimensionPixelSize(R.dimen._20sdp).toFloat()
                        )
                        objAnimator.duration = 80
                        objAnimator.start()
                    }
                }
            }

        })
    }

    private fun playAnimation() {
        nested_scroll_view.startAnimation(loadAnimation(activity, R.anim.slide_up))
    }

    private fun bind(event: Event) {
        Glide.with(this)
            .load(Config.BASE_URL_UPLOADS + event.thumbnail)
            .into(iv_thumbnail)

        tv_title.text = event.title
        tv_by.text = "by Dicoding"
        tv_date.text = "${event.startsOn} - ${event.endsOn}"
        tv_location.text = event.location.text
        tv_description.text = event.description
    }

    private fun setupPhotos(photos: Array<String>) {
        val adapter = PhotosAdapter(photos, object : PhotosAdapter.Listener {
            override fun onClick(position: Int) {
                Toast.makeText(activity, "Fullscreen image", Toast.LENGTH_SHORT).show()
            }
        })
        rv_photos.adapter = adapter
        rv_photos.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        lbl_photos.visibility = View.VISIBLE
        rv_photos.visibility = View.VISIBLE
    }
}