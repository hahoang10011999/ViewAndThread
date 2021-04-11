package com.example.viewandthread

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.ImageView

import kotlinx.android.synthetic.main.activity_main.*

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
    var run1 = Runnable {
        RunToZero()
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
                    hand.removeCallbacks(run1)
                    hand.postDelayed(RunnableAutoMinus, 1000)
                }
                MotionEvent.ACTION_UP -> {
                    hand.removeCallbacks(RunnableAutoMinus)
                    hand.postDelayed(run1, 2000)
                }
            }
            false
        }
        btnPlus.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    hand.removeCallbacks(run)
                    hand.removeCallbacks(run1)
                    hand.postDelayed(RunnableAutoPlus, 1000)
                }
                MotionEvent.ACTION_UP -> {
                    hand.removeCallbacks(RunnableAutoPlus)
                    hand.postDelayed(run1, 2000)
                }
            }
            false
        }
        tvNumber.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    y = motionEvent.getY()
                    hand.removeCallbacks(run1)
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
                    hand.postDelayed(run1, 2000)
                }
            }
            true
        }
    }
    fun FunAutoMinus(){
        hand.postDelayed(RunnableAutoMinus,100)
    }
    fun FunAutoPlus() {
        hand.postDelayed(RunnableAutoPlus, 100)
    }

    fun RunToZero() {
        hand.postDelayed(run, 100)
    }

    fun Minus() {
        hand.removeCallbacks(run1)
        hand.removeCallbacks(run)
        number--
        tvNumber.text = number.toString()
        hand.postDelayed(run1, 2000)
        SetTextColor()
    }

    fun Plus() {
        hand.removeCallbacks(run1)
        hand.removeCallbacks(run)
        number++
        tvNumber.text = number.toString()
        hand.postDelayed(run1, 2000)
        SetTextColor()
    }
    fun SetTextColor(){
        if(number == 0){
            tvNumber.setTextColor(Color.BLACK)
        }
        if (number % 150 == 0) {
            tvNumber.setTextColor(Color.BLUE)
        }else if(number % 250 == 0){
            tvNumber.setTextColor(Color.RED)
        }else if(number % 350 == 0){
            tvNumber.setTextColor(Color.YELLOW)
        }
    }
}