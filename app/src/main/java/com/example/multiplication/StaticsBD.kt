package com.example.multiplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.databinding.ActivityStaticsBdBinding

class StaticsBD : AppCompatActivity() {
    private lateinit var binding: ActivityStaticsBdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaticsBdBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}