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
import com.example.multiplication.databinding.ActivityMultiBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ActivityMulti : AppCompatActivity() {
    private lateinit var binding: ActivityMultiBinding

    private var start: Int = 0
    private var end: Int = 9

    private val multi = NumberMulti(start, end)

    private lateinit var repository: Repository.BaseMulti
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        multi.show(binding.answer, binding.expression)

        val source = ImageDecoder.createSource(
            resources, R.drawable.win
        )
        val drawable = ImageDecoder.decodeDrawable(source)
        binding.win.setImageDrawable(drawable)
        (drawable as? AnimatedImageDrawable)?.start()
        binding.win.visibility = View.INVISIBLE

        binding.btnDel.setOnClickListener {
            multi.show_delete(binding.answer)
        }

        repository = Repository.BaseMulti(Core(this).daomulti(), Now.Base())

        binding.btnOk.setOnClickListener {
            if (multi.ischeak(binding.answer.text.toString())) {
                viewModelScope.launch(Dispatchers.IO) {
                    multi.Item(true, repository)
                }
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()

                multi.show(binding.answer, binding.expression)

                binding.win.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.win.visibility = View.INVISIBLE
                }, 1000)

            } else {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                viewModelScope.launch(Dispatchers.IO) {
                    multi.Item(false, repository)
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