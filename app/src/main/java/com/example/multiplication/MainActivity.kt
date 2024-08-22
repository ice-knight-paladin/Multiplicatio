package com.example.multiplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnMulti.setOnClickListener {
            val intent = Intent(this, ActivityType::class.java)
            intent.putExtra(Keys.KEY_TYPE, "multi")
            startActivity(intent)
            finish()
        }

        binding.btnStatic.setOnClickListener {
            startActivity(Intent(this, StaticsBD::class.java))
            finish()
        }

//        binding.btnClearTable.setOnClickListener {
//            ClearBottomSheetFragment().show(supportFragmentManager, "createClearFragment")
//        }

        binding.btnDivision.setOnClickListener {
            val intent = Intent(this, ActivityType::class.java)
            intent.putExtra(Keys.KEY_TYPE, "div")
            startActivity(intent)
            finish()
        }

        binding.btnOption.setOnClickListener{
            OptionStartSheetDialogFragment().show(supportFragmentManager, "createOptionFragment")
        }
    }
}