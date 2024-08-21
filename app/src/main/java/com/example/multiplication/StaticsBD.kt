package com.example.multiplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.databinding.ActivityStaticsBdBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class StaticsBD : AppCompatActivity() {
    private lateinit var binding: ActivityStaticsBdBinding
    private lateinit var list: MutableList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaticsBdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository_multi = Repository.BaseMulti(Core(this).daomulti(), Now.Base())
        val repository_div = Repository.BaseDiv(Core(this).daodiv(), Now.Base())
        val myAdapter = MyItemsAdapter()
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
            list = repository_multi.list().toMutableList()
            list.addAll(repository_div.list())
        }


        binding.recyclerview.adapter = myAdapter

        Handler(Looper.getMainLooper()).postDelayed(
            {
                for (i in list) {
                    myAdapter.add(LinearLayout(this), i)
                }
            },
            1000
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}