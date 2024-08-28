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
import com.example.multiplication.Option.levelminus
import com.example.multiplication.Option.levelmulti
import com.example.multiplication.Option.levelplus
import com.example.multiplication.Option.minusmax
import com.example.multiplication.Option.minusmin
import com.example.multiplication.Option.multimax
import com.example.multiplication.Option.multimin
import com.example.multiplication.Option.plusmax
import com.example.multiplication.Option.plusmin
import com.example.multiplication.databinding.ActivityLevelBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LevelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLevelBinding
    private var start: Int = 1
    private var end: Int = 9
    private var START_TIME = 10000L
    private lateinit var mCountDownTimer: CountDownTimer
    private var mTimeLeftInMillis = START_TIME
    private var NAME_TYPE = 0

    private var number_textview = 0

    private var repository = mutableListOf<Repository>()
    private lateinit var repository_save: Repository.BaseSave
    private var multi = mutableListOf<NumberClass>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NAME_TYPE = check_type()
        number_textview = intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() * 10
        START_TIME -= 500L * intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt()

        repository.add(Repository.BaseMulti(Core(this).daomulti(), Now.Base()))
        repository.add(Repository.BaseDiv(Core(this).daodiv(), Now.Base()))
        repository.add(Repository.BasePlus(Core(this).daoplus(), Now.Base()))
        repository.add(Repository.BaseMinus(Core(this).daominus(), Now.Base()))

        repository_save = Repository.BaseSave(Core(this).daosave(), Now.Base())

        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
            if (NAME_TYPE == 0) {
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
            } else if (NAME_TYPE == 1) {
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
            } else if (NAME_TYPE == 2) {
                if (repository_save.item(plusmin) == null) {
                    repository_save.add(plusmin, 1)
                    start = repository_save.item(plusmin)!!.number
                } else {
                    start = repository_save.item(plusmin)!!.number
                }
                if (repository_save.item(plusmax) == null) {
                    repository_save.add(plusmax, 9)
                    end = repository_save.item(plusmax)!!.number
                } else {
                    end = repository_save.item(plusmax)!!.number
                }
            } else if (NAME_TYPE == 3) {
                if (repository_save.item(minusmin) == null) {
                    repository_save.add(minusmin, 1)
                    start = repository_save.item(minusmin)!!.number
                } else {
                    start = repository_save.item(minusmin)!!.number
                }
                if (repository_save.item(minusmax) == null) {
                    repository_save.add(minusmax, 9)
                    end = repository_save.item(minusmax)!!.number
                } else {
                    end = repository_save.item(minusmax)!!.number
                }
            }
        }
        Handler().postDelayed(
            {
                multi.add(NumberMulti(start, end))
                multi.add(NumberDiv(start, end))
                multi.add(NumberPlus(start, end))
                multi.add(NumberMinus(start, end))
                multi[NAME_TYPE].show(binding.answer, binding.expression)
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
            multi[NAME_TYPE].show_delete(binding.answer)
            startTimer()
        }



        binding.btnOk.setOnClickListener {
            if (multi[NAME_TYPE].ischeak(binding.answer.text.toString())) {
                CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
                    multi[NAME_TYPE].Item(true, repository[NAME_TYPE])
                }
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                resetTimer()
                multi[NAME_TYPE].show(binding.answer, binding.expression)

                binding.win.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.win.visibility = View.INVISIBLE
                }, 1000)

                number_textview -= 1
                if (number_textview == 0) {
                    if (check_type() == 0) {
                        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(
                            Dispatchers.IO
                        ) {
                            repository_save.update(
                                if (intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() < 10)
                                    intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() + 1
                                else 10, repository_save.item(levelmulti)!!.text
                            )
                        }
                    } else if (check_type() == 1) {
                        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(
                            Dispatchers.IO
                        ) {
                            repository_save.update(
                                if (intent.extras!!.getString(Keys.KEY_LEVEL)!!
                                        .toInt() < 10
                                ) intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() + 1 else 10,
                                repository_save.item(leveldiv)!!.text
                            )
                        }
                    } else if (check_type() == 2) {
                        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(
                            Dispatchers.IO
                        ) {
                            repository_save.update(
                                if (intent.extras!!.getString(Keys.KEY_LEVEL)!!
                                        .toInt() < 10
                                ) intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() + 1 else 10,
                                repository_save.item(levelplus)!!.text
                            )
                        }
                    } else if (check_type() == 3) {
                        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(
                            Dispatchers.IO
                        ) {
                            repository_save.update(
                                if (intent.extras!!.getString(Keys.KEY_LEVEL)!!
                                        .toInt() < 10
                                ) intent.extras!!.getString(Keys.KEY_LEVEL)!!.toInt() + 1 else 10,
                                repository_save.item(levelminus)!!.text
                            )
                        }
                    }
                    startActivity(Intent(this, LevelsActivity::class.java))
                    finish()
                } else {
                    if (mCountDownTimer != null) {
                        mCountDownTimer.cancel()
                        resetTimer()
                    }
                }

            } else {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
                    multi[NAME_TYPE].Item(false, repository[NAME_TYPE])
                }
            }
        }
    }


    fun check_type(): Int {
        if (intent.extras?.getString(Keys.KEY_TYPE) == "multi") {
            return 0
        } else if (intent.extras?.getString(Keys.KEY_TYPE) == "div") {
            return 1
        } else if (intent.extras?.getString(Keys.KEY_TYPE) == "plus") {
            return 2
        } else if (intent.extras?.getString(Keys.KEY_TYPE) == "minus") {
            return 3
        }
        return 0
    }

    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 10) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateprogressbar()
            }

            override fun onFinish() {
                mTimeLeftInMillis = 0
                updateprogressbar()
                multi[NAME_TYPE].show(binding.answer, binding.expression)
                resetTimer()
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
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