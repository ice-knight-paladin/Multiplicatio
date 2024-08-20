package com.example.multiplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMulti.setOnClickListener {
            val intent = Intent(this, Multi::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnStatic.setOnClickListener {
            startActivity(Intent(this, StaticsBD::class.java))
            finish()
        }

//        val r = CoreMulti(this).daodiv()
//        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
//            r.add(ItemCacheDiv(11L, "ddd", 1, 0))
//            delay(10000)
//        }

        binding.btnClearTable.setOnClickListener {
            ClearBottomSheetFragment().show(supportFragmentManager, "createClearFragment")
        }

        binding.btnDivision.setOnClickListener {
            val intent = Intent(this, ActivityDiv::class.java)
            startActivity(intent)
            finish()
        }
    }
}