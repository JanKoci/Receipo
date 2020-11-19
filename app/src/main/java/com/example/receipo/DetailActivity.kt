package com.example.receipo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.max
import kotlin.math.min

class DetailActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private var currentAnimator: Animator? = null

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private var shortAnimationDuration: Int = 0

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar: Toolbar = findViewById(R.id.detail_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        println("fuck")
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // get data from Intent
        val receiptData = intent.getStringArrayExtra(DETAIL_EXTRA_MESSAGE)
        val shopName = receiptData?.get(0)
        val creationDate = receiptData?.get(1)
        val price = receiptData?.get(2)
        // set given values
        findViewById<TextView>(R.id.store_name_text_view).apply {
            text = shopName
        }
        findViewById<TextView>(R.id.creation_date_value).apply {
            text = creationDate
        }
        findViewById<TextView>(R.id.expiration_date_value).apply {
            text = creationDate
        }
        findViewById<TextView>(R.id.price_text_view).apply {
            text = price
        }
        val detail_content_layout: ConstraintLayout = findViewById(R.id.detail_layout)

        val dataSource = DataSource(this).getData()
        val recyclerView: RecyclerView = findViewById(R.id.detail_recycle_view)
        recyclerView.adapter = DetailListAdapter(dataSource)

        // Hook up clicks on the thumbnail views.
        val thumbnailView: View = findViewById(R.id.detail_receipt_scan_thumbnail)
        thumbnailView.setOnClickListener {
            zoomImageFromThumb(thumbnailView, R.drawable.receipt_example)
        }


        scaleGestureDetector = ScaleGestureDetector(
            this,
            ScaleListener()
        )

        // Retrieve and cache the system's default "short" animation time.
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        //AppBarConfiguration(setOf(
        //    R.id.nav_home, R.id.nav_expired, R.id.nav_stats), detail_content_layout)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }

    private inner class ScaleListener: ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            val scaledImageView: ImageView = findViewById(R.id.detail_receipt_scan_enlarged)
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            scaledImageView.scaleX = scaleFactor
            scaledImageView.scaleY = scaleFactor
            return true
        }
    }

    private fun zoomImageFromThumb(thumbView: View, imageResId: Int) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentAnimator?.cancel()
        println("ahojahojahoj")
        // Load the high-resolution "zoomed-in" image.
        val expandedImageView: ImageView = findViewById(R.id.detail_receipt_scan_enlarged)
        expandedImageView.setImageResource(imageResId)

        val contentLayout: LinearLayout = findViewById(R.id.detail_content_layout)
        val appBarLayout: AppBarLayout = findViewById(R.id.detail_appbar)

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBoundsInt)
        findViewById<View>(R.id.detail_scan_container)
            .getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.alpha = 0f
        expandedImageView.visibility = View.VISIBLE
        contentLayout.visibility = View.GONE
        appBarLayout.visibility = View.GONE

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    expandedImageView,
                    View.X,
                    startBounds.left,
                    finalBounds.left)
            ).apply {
                with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
//        expandedImageView.setOnClickListener {
//            currentAnimator?.cancel()
//
//            // Animate the four positioning/sizing properties in parallel,
//            // back to their original values.
//            currentAnimator = AnimatorSet().apply {
//                play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).apply {
//                    with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
//                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale))
//                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale))
//                }
//                duration = shortAnimationDuration.toLong()
//                interpolator = DecelerateInterpolator()
//                addListener(object : AnimatorListenerAdapter() {
//
//                    override fun onAnimationEnd(animation: Animator) {
//                        thumbView.alpha = 1f
//                        expandedImageView.visibility = View.GONE
//                        contentLayout.visibility = View.VISIBLE
//                        appBarLayout.visibility = View.VISIBLE
//                        currentAnimator = null
//                    }
//
//                    override fun onAnimationCancel(animation: Animator) {
//                        thumbView.alpha = 1f
//                        expandedImageView.visibility = View.GONE
//                        contentLayout.visibility = View.VISIBLE
//                        appBarLayout.visibility = View.VISIBLE
//                        currentAnimator = null
//                    }
//                })
//                start()
//            }
//        }
    }
}