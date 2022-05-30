package com.kixfobby.softwarelab.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.kixfobby.softwarelab.R

const val MAX_STEP = 3

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var viewPager: ViewPager? = null
    private val aboutTitleArray = arrayOf("Quality, Convenient, Local", "Door Delivery", "Love the Earth")
    private val aboutDescriptionArray = arrayOf(
        "Sell your farm fresh products directly to consumers, cutting out the middleman and reducing emissions of the global supply chain.",
        "Our team of delivery drivers will make sure your orders are picked up on time and promptly delivered to your customers.",
        "We love the earth and know you do too! Join us in reducing our carbon footprint one order at a time. "
    )
    private val bgImagesArray = intArrayOf(R.drawable.image_15, R.drawable.image_10, R.drawable.image_3)
    private var myViewPagerAdapter: MyViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewPager = findViewById<View>(R.id.view_pager) as ViewPager

        // adding bottom dots
        bottomProgressDots(0)
        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager!!.adapter = myViewPagerAdapter
        viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)
    }

    fun bottomProgressDots(current_index: Int) {
        val dotsLayout = findViewById<View>(R.id.layoutDots) as LinearLayout
        val dots = arrayOfNulls<ImageView>(MAX_STEP)
        dotsLayout.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val widthHeight = 15
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(widthHeight, widthHeight))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            dots[i]!!.setColorFilter(ContextCompat.getColor(this, R.color.grey_20), PorterDuff.Mode.SRC_IN)
            dotsLayout.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[current_index]!!.setImageResource(R.drawable.shape_circle)
            dots[current_index]!!.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        }
    }

    //  viewpager change listener
    private val viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            bottomProgressDots(position)
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null
        private var btnNext: TextView? = null

        @SuppressLint("SetTextI18n")
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = layoutInflater!!.inflate(R.layout.item_card_wizard_bg, container, false)

            view.findViewById<TextView>(R.id.title).text = aboutTitleArray[position]
            view.findViewById<TextView>(R.id.description).text = aboutDescriptionArray[position]
            view.findViewById<ImageView>(R.id.image_bg).setImageResource(bgImagesArray[position])

            btnNext = view.findViewById(R.id.btn_next)
            if (position == aboutTitleArray.size - 1) btnNext!!.text = "Get Started" else btnNext!!.text = "Login"

            btnNext!!.setOnClickListener {
                val current = viewPager!!.currentItem + 1
                // move to next screen
                if (current < MAX_STEP) viewPager!!.currentItem = current else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }
            }
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return aboutTitleArray.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

}