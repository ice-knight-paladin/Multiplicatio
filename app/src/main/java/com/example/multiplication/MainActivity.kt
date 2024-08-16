package com.example.multiplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var repository: Repository.Base

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = Repository.Base(Core(this).dao(), Now.Base())
        kotlin.runCatching {
            repository.add("DDDD", 1, 0)
        }


        binding.btnMulti.setOnClickListener {
            startActivity(Intent(this, Multi::class.java))
            finish()
        }

        binding.btnStatic.setOnClickListener {
            startActivity(Intent(this, StaticsBD::class.java))
            finish()
        }
    }
}