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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMulti.setOnClickListener {
            startActivity(Intent(this, Multi::class.java))
            finish()
        }

        binding.btnStatic.setOnClickListener {
            startActivity(Intent(this, StaticsBD::class.java))
            finish()
        }

       // val repository = Repository.Base(Core(this).dao(), Now.Base())

        binding.btnClearTable.setOnClickListener{
            ClearBottomSheetFragment().show(supportFragmentManager, "createClearFragment")
        }
    }
}