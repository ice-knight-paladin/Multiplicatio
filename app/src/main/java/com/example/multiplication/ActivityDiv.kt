package com.example.multiplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.multiplication.databinding.ActivityDivBinding

class ActivityDiv : AppCompatActivity() {
    private lateinit var binding : ActivityDivBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDivBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}