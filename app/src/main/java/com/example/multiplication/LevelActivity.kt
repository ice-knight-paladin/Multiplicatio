package com.example.multiplication

import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.Option.divmax
import com.example.multiplication.Option.divmin
import com.example.multiplication.Option.leveldiv
import com.example.multiplication.Option.levelmulti
import com.example.multiplication.Option.multimax
import com.example.multiplication.Option.multimin
import com.example.multiplication.databinding.ActivityLevelBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LevelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLevelBinding
    private var start: Int = 1
    private var end: Int = 9
    private var START_TIME = 10000L
    private lateinit var mCountDownTimer: CountDownTimer
    private var mTimeLeftInMillis = START_TIME

    private lateinit var multi: NumberClass
    private var number_textview = 0

    //private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private lateinit var repository: Repository
    private lateinit var repository_save: Repository.BaseSave


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = RepositoryFactory.createRepository(check_type()).getRepository(this)
        repository_save = Repository.BaseSave(Core(this).daosave(), Now.Base())

        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
            START_TIME = START_TIME - intent.extras!!.getString(Keys.KEY_LEVEL)!!.toLong() * 500
            number_textview = intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() * 10
            if (check_type()) {
                if (repository_save.item(multimin) == null) {
                    repository_save.add(multimin, 1)
                    start = repository_save.item(multimin)!!.number
                } else {
                    start = repository_save.item(multimin)!!.number
                }
                if (repository_save.item(multimax) == null) {
                    repository_save.add(multimax, 9)
                    end = repository_save.item(multimax)!!.number
                } else {
                    end = repository_save.item(multimax)!!.number
                }
                multi = NumberMulti(start, end)
            } else {

                if (repository_save.item(divmin) == null) {
                    repository_save.add(divmin, 1)
                    start = repository_save.item(divmin)!!.number
                } else {
                    start = repository_save.item(divmin)!!.number
                }
                if (repository_save.item(divmax) == null) {
                    repository_save.add(divmax, 9)
                    end = repository_save.item(divmax)!!.number
                } else {
                    end = repository_save.item(divmax)!!.number
                }
                multi = NumberDiv(start, end)
            }
        }
        Handler().postDelayed(
            {
                if (check_type()) (multi as NumberMulti).show(binding.answer, binding.expression)
                else (multi as NumberDiv).show(binding.answer, binding.expression)
                startTimer()
            }, 500
        )


        val source = ImageDecoder.createSource(
            resources, R.drawable.win
        )
        val drawable = ImageDecoder.decodeDrawable(source)
        binding.win.setImageDrawable(drawable)
        (drawable as? AnimatedImageDrawable)?.start()
        binding.win.visibility = View.INVISIBLE

        binding.btnDel.setOnClickListener {
            if (check_type()) (multi as NumberMulti).show_delete(binding.answer)
            else (multi as NumberDiv).show_delete(binding.answer)
            startTimer()
        }



        binding.btnOk.setOnClickListener {
            if (multi.ischeak(binding.answer.text.toString())) {
                CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
                    if (check_type()) (multi as NumberMulti).Item(
                        true,
                        repository as Repository.BaseMulti
                    )
                    else (multi as NumberDiv).Item(true, repository as Repository.BaseDiv)
                }

                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()


                if (check_type()) (multi as NumberMulti).show(binding.answer, binding.expression)
                else (multi as NumberDiv).show(binding.answer, binding.expression)

                binding.win.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.win.visibility = View.INVISIBLE
                }, 1000)
                number_textview -= 1
                if (number_textview == 0) {
                    if (check_type()) {
                        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
                            repository_save.update(
                                if (intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() < 10)
                                    intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() + 1
                                else 10, repository_save.item(levelmulti)!!.text)
                        }
                    } else {
                        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
                            repository_save.update(if (intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() < 10) intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() + 1 else 10, repository_save.item(leveldiv)!!.text)
                        }
                    }
                    startActivity(Intent(this, LevelsActivity::class.java))
                    finish()
                } else resetTimer()

            } else {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
                    if (check_type()) (multi as NumberMulti).Item(
                        false,
                        repository as Repository.BaseMulti
                    )
                    else (multi as NumberDiv).Item(false, repository as Repository.BaseDiv)
                }
            }
        }
    }


    fun check_type(): Boolean {
        if (intent.extras?.getString(Keys.KEY_TYPE)!!.contains("multi")) {
            return true
        } else {
            return false
        }
    }

    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateprogressbar()
            }

            override fun onFinish() {
                mTimeLeftInMillis = 0
                updateprogressbar()
                resetTimer()
                //startActivity(Intent(this@ActivityType, MainActivity::class.java))
                //finish()
            }
        }.start()
    }

    private fun resetTimer() {
        mCountDownTimer.cancel()
        mTimeLeftInMillis = START_TIME
        startTimer()
    }

    fun updateprogressbar() {
        val progress = ((mTimeLeftInMillis / START_TIME.toDouble()) * 100).toInt()
        binding.progressBar.progress = progress
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LevelsActivity::class.java))
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.slide_in_left, R.anim.slide_out_left)
//        }
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


    class MultiRepository : Repository.BaseRepository {
        override fun getRepository(context: Context): Repository {
            return Repository.BaseMulti(Core(context).daomulti(), Now.Base())
        }
    }

    class DivRepository : Repository.BaseRepository {
        override fun getRepository(context: Context): Repository {
            return Repository.BaseDiv(Core(context).daodiv(), Now.Base())
        }
    }
}