package com.example.multiplication

import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.databinding.ActivityDivBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

class ActivityDiv : AppCompatActivity() {
    private lateinit var binding : ActivityDivBinding
    private lateinit var repository: Repository.BaseDiv
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)


    private var number_one: Int = 0
    private var number_two: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDivBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = Repository.BaseDiv(Core(this).daodiv(), Now.Base())

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
            if (binding.answer.text.toString().length == (number_one).toString().length && binding.answer.text.toString().toInt() == number_one) {
                viewModelScope.launch(Dispatchers.IO) {
                    isItem(true)
                }
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                random_numbers()
                binding.win.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.win.visibility = View.INVISIBLE
                }, 2000)

            } else {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                viewModelScope.launch(Dispatchers.IO) {
                    isItem(false)
                }
            }
        }
    }

    suspend fun isItem(i: Boolean, ) {
        if (repository.item("${number_one*number_two} / ${number_two}") == null) {
            if (i)
                repository.add("${number_one*number_two} / ${number_two}}", 1, 0)
            else
                repository.add("${number_one*number_two} / ${number_two}}", 0, 1)
        } else {
            repository.update("${number_one*number_two} / ${number_two}}", i)
        }
    }

    fun random_numbers() {
        number_one = (0..9).random()
        number_two = (1..9).random()
        binding.expression.text = "${number_one * number_two} / $number_two="
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