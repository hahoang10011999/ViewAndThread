package com.example.viewandthread

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.ImageView

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var number: Int = 0
    var hand = Handler()
    var y: Float = 0f
    var run = Runnable {
        if (number > 0) {
            number--
            tvNumber.text = number.toString()
            RunToZero()
        }
        if (number < 0) {
            number++
            tvNumber.text = number.toString()
            RunToZero()
        }
        SetTextColor()
    }
    var RunnableAutoPlus = Runnable {
        number++
        tvNumber.text = number.toString()
        FunAutoPlus()
        SetTextColor()
    }
    var RunnableAutoMinus = Runnable {
        number--
        tvNumber.text = number.toString()
        FunAutoMinus()
        SetTextColor()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnMinus.setOnClickListener {
            Minus()
        }
        btnPlus.setOnClickListener {
            Plus()
        }
        btnMinus.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    hand.removeCallbacks(run)
                    hand.postDelayed(RunnableAutoMinus, 1000)
                }
                MotionEvent.ACTION_UP -> {
                    hand.removeCallbacks(RunnableAutoMinus)
                    hand.postDelayed(run, 2000)
                }
            }
            false
        }
        btnPlus.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    hand.removeCallbacks(run)
                    hand.postDelayed(RunnableAutoPlus, 1000)
                }
                MotionEvent.ACTION_UP -> {
                    hand.removeCallbacks(RunnableAutoPlus)
                    hand.postDelayed(run, 2000)
                }
            }
            false
        }
        tvNumber.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    y = motionEvent.getY()
                    hand.removeCallbacks(run)
                }
                MotionEvent.ACTION_MOVE -> {
                    if (y > motionEvent.getY()) {
                        number++
                        tvNumber.text = number.toString()
                        SetTextColor()
                    }
                    if (y < motionEvent.getY()) {
                        number--
                        tvNumber.text = number.toString()
                        SetTextColor()
                    }
                    y = motionEvent.getY()
                }
                MotionEvent.ACTION_UP -> {
                    hand.postDelayed(run, 2000)
                }
            }
            true
        }
    }

    fun FunAutoMinus() {
        hand.postDelayed(RunnableAutoMinus, 10)
    }

    fun FunAutoPlus() {
        hand.postDelayed(RunnableAutoPlus, 10)
    }

    fun RunToZero() {
        hand.postDelayed(run, 10)
    }

    fun Minus() {
        hand.removeCallbacks(run)
        number--
        tvNumber.text = number.toString()
        hand.postDelayed(run, 2000)
        SetTextColor()
    }

    fun Plus() {
        hand.removeCallbacks(run)
        number++
        tvNumber.text = number.toString()
        hand.postDelayed(run, 2000)
        SetTextColor()
    }

    fun SetTextColor() {
        var random = Random()
        val color: Int =
            Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
        if (number % 100 == 0) tvNumber.setTextColor(color)
    }
}