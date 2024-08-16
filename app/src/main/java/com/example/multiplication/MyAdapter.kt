package com.example.multiplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.multiplication.databinding.ItemRecyclerBinding
import org.w3c.dom.Text

class MyItemsAdapter : RecyclerView.Adapter<MyItemViewHolder>() {
    private val itemList = ArrayList<LinearLayout>()
    private var element: MutableList<Item> = ArrayList<Item>()

    fun add(linearLayout: LinearLayout, i: Item) {
        element.add(i)
        itemList.add(linearLayout)
        notifyItemInserted(itemCount - 1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        return MyItemViewHolder(ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        holder.text_name.text = element[position].text
        holder.text_correct.text = element[position].correct.toString()
        holder.text_incorrect.text = element[position].incorrect.toString()
    }
}

class MyItemViewHolder(private val binding: ItemRecyclerBinding) : ViewHolder(binding.root) {
    public lateinit var text_name:TextView
    public lateinit var text_correct:TextView
    public lateinit var text_incorrect:TextView

    init {
        text_name = binding.name
        text_correct = binding.correct
        text_incorrect = binding.incorrect
    }
}