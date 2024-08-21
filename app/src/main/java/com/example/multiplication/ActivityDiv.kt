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


    private var start: Int = 1
    private var end: Int = 9

    private var div = NumberDiv(start, end)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDivBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = Repository.BaseDiv(Core(this).daodiv(), Now.Base())

        div.show(binding.answer, binding.expression)

        val source = ImageDecoder.createSource(
            resources, R.drawable.win
        )
        val drawable = ImageDecoder.decodeDrawable(source)
        binding.win.setImageDrawable(drawable)
        (drawable as? AnimatedImageDrawable)?.start()
        binding.win.visibility = View.INVISIBLE


        binding.btnDel.setOnClickListener {
            div.show_delete(binding.answer)
        }


        binding.btnOk.setOnClickListener {
            if (div.ischeak(binding.answer.text.toString())) {
                viewModelScope.launch(Dispatchers.IO) {
                    div.Item(true, repository)
                }
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()

                div.show(binding.answer, binding.expression)

                binding.win.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.win.visibility = View.INVISIBLE
                }, 1000)

            } else {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                viewModelScope.launch(Dispatchers.IO) {
                    div.Item(false, repository)
                }
            }
        }
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.answer.text = savedInstanceState.getString(Keys.KEY_ANSWER)
        binding.expression.text = savedInstanceState.getString(Keys.KEY_EXP)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Keys.KEY_ANSWER, binding.answer.text.toString())
        outState.putString(Keys.KEY_EXP, binding.expression.text.toString())
    }
}