package com.example.multiplication

import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.databinding.ActivityMultiBinding
import kotlinx.coroutines.delay

class Multi : AppCompatActivity() {
    private lateinit var binding: ActivityMultiBinding

    private var number_one: Int = 0
    private var number_two: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        random_numbers()

        val source = ImageDecoder.createSource(
            resources, R.drawable.win
        )
        val drawable = ImageDecoder.decodeDrawable(source)
        binding.win.setImageDrawable(drawable)
        (drawable as? AnimatedImageDrawable)?.start()
        binding.win.visibility = View.INVISIBLE

        binding.btnDel.setOnClickListener {
            if (binding.answer.text.toString().length != 0)
                binding.answer.text = binding.answer.text.toString()
                    .subSequence(0, binding.answer.text.toString().length - 1)
        }

        binding.btnOk.setOnClickListener {
            if (binding.answer.text.toString().length == (number_one * number_two).toString().length && binding.answer.text.toString()
                    .toInt() == number_one * number_two
            ) {
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                random_numbers()
                binding.win.visibility = View.VISIBLE
                Handler().postDelayed({
                    binding.win.visibility = View.INVISIBLE
                }, 2000)
            } else
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
        }
    }

    fun random_numbers() {
        number_one = (0..9).random()
        number_two = (0..9).random()
        binding.expression.text = "$number_one * $number_two"
        binding.answer.text = ""
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun click_number(view: View) {
        if (binding.answer.text.toString().length <= 5)
            binding.answer.text = binding.answer.text.toString() + (view as Button).text
    }
}