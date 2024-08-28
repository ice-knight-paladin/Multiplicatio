package com.example.multiplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.Option.leveldiv
import com.example.multiplication.Option.levelminus
import com.example.multiplication.Option.levelmulti
import com.example.multiplication.Option.levelplus
import com.example.multiplication.databinding.ActivityLevelsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LevelsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLevelsBinding
    private var list_multi = mutableListOf<TextView>()
    private var list_div = mutableListOf<TextView>()
    private var list_plus = mutableListOf<TextView>()
    private var list_minus = mutableListOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (i in 1..10) {
            var textviewID = "multitext$i"
            var resID = resources.getIdentifier(textviewID, "id", packageName)
            list_multi.add(findViewById(resID))
            findViewById<TextView>(resID).id = i
        }
        for (i in 1..10) {
            var textviewID = "divtext$i"
            var resID = resources.getIdentifier(textviewID, "id", packageName)
            list_div.add(findViewById(resID))
            findViewById<TextView>(resID).id = i + 10
        }
        for (i in 1..10) {
            var textviewID = "plustext$i"
            var resID = resources.getIdentifier(textviewID, "id", packageName)
            list_plus.add(findViewById(resID))
            findViewById<TextView>(resID).id = i + 20
        }
        for (i in 1..10) {
            var textviewID = "minustext$i"
            var resID = resources.getIdentifier(textviewID, "id", packageName)
            list_minus.add(findViewById(resID))
            findViewById<TextView>(resID).id = i + 30
        }

        val repository = Repository.BaseSave(Core(this).daosave(), Now.Base())
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
            if (repository.item(levelmulti) == null) {
                repository.add(levelmulti, 1)
                withContext(Dispatchers.Main) {
                    binding.multitext1.isEnabled = true
                }

            } else for (i in 0..<repository.item(levelmulti)!!.number) withContext(Dispatchers.Main) {
                list_multi[i].isEnabled = true
            }
            if (repository.item(leveldiv) == null) {
                repository.add(leveldiv, 1)
                withContext(Dispatchers.Main) {
                    binding.divtext1.isEnabled = true
                }

            } else for (i in 0..<repository.item(leveldiv)!!.number) withContext(Dispatchers.Main) {
                list_div[i].isEnabled = true
            }
            if (repository.item(levelplus) == null) {
                repository.add(levelplus, 1)
                withContext(Dispatchers.Main){
                    binding.plustext1.isEnabled = true
                }
            } else for (i in 0..<repository.item(levelplus)!!.number) withContext(Dispatchers.Main) {
                list_plus[i].isEnabled = true
            }
            if (repository.item(levelminus) == null) {
                repository.add(levelminus, 1)
                withContext(Dispatchers.Main){
                    binding.minustext1.isEnabled = true
                }
            }else for (i in 0..<repository.item(levelminus)!!.number) withContext(Dispatchers.Main) {
                list_minus[i].isEnabled = true
            }
        }
    }

    fun click_level(view: View) {
        val intent = Intent(this, LevelActivity::class.java)
        if (view.id <= 10) {
            intent.putExtra(Keys.KEY_TYPE, "multi")
        } else if (view.id <= 20) {
            intent.putExtra(Keys.KEY_TYPE, "div")
        } else if (view.id <= 30) {
            intent.putExtra(Keys.KEY_TYPE, "plus")
        } else if (view.id <= 40) {
            intent.putExtra(Keys.KEY_TYPE, "minus")
        }
        intent.putExtra(Keys.KEY_LEVEL, (view as TextView).text)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }
}