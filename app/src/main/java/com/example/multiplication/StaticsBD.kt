package com.example.multiplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplication.databinding.ActivityStaticsBdBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class StaticsBD : AppCompatActivity() {
    private lateinit var binding: ActivityStaticsBdBinding
    private lateinit var list: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaticsBdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository.Base(Core(this).dao(), Now.Base())
        val myAdapter = MyItemsAdapter()
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
            list = repository.list()

        }


        binding.recyclerview.adapter = myAdapter
        var k = 0

        Handler().postDelayed({
                for (i in list) {
                    val layout = LinearLayout(this)
                    layout.orientation = LinearLayout.VERTICAL
                    layout.layoutParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.FILL_PARENT
                        )
                    layout.id = k
                    k++
                    myAdapter.add(layout, i)
                    Log.d("MyTag", "${i.text}")
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