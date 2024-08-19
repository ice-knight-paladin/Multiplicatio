package com.example.multiplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import com.example.multiplication.Keys

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMulti.setOnClickListener {
            val intent = Intent(this, Multi::class.java)
            //intent.putExtra(Keys.KEY_MULT, "+")
            startActivity(intent)
            finish()
        }

        binding.btnStatic.setOnClickListener {
            startActivity(Intent(this, StaticsBD::class.java))
            finish()
        }

        binding.btnClearTable.setOnClickListener{
            ClearBottomSheetFragment().show(supportFragmentManager, "createClearFragment")
        }

        binding.btnDivision.setOnClickListener{
            val intent = Intent(this, Multi::class.java)
            //.putExtra(Keys.KEY_MULT, "-")
            startActivity(intent)
            finish()
        }
    }
}