package com.example.multiplication

import android.widget.TextView
import kotlin.math.max
import kotlin.math.min


abstract class NumberClass(
    private val start: Int,
    private val end: Int,
) {
    public var number_one = (start..end).random()
    public var number_two = (start..end).random()

    open fun random(){
        number_one = (start..end).random()
        number_two = (start..end).random()
    }

    abstract fun ischeak(answer:String):Boolean

}

class NumberMulti(start: Int, end: Int) : NumberClass(start, end) {
    private var multi = number_one * number_two

    override fun ischeak(answer: String):Boolean {
        return answer.length == multi.toString().length && answer.toInt() == multi
    }

    override fun random(){
        super.random()
        multi = number_one * number_two
    }


    fun Item(i: Boolean, repository: Repository.BaseMulti) {
        if (repository.item(
                "${min(number_one, number_two)} * ${
                    max(
                        number_one,
                        number_two
                    )
                }"
            ) == null
        ) {
            if (i)
                repository.add("${min(number_one, number_two)} * ${max(number_one, number_two)}", 1, 0)
            else
                repository.add("${min(number_one, number_two)} * ${max(number_one, number_two)}", 0, 1)
        } else {
            repository.update("${min(number_one, number_two)} * ${max(number_one, number_two)}", i)
        }
    }

    fun show(answer: TextView, expression:TextView){
        random()
        answer.text = ""
        expression.text = "$number_one * $number_two="
    }

    fun show_delete(answer: TextView){
        if (answer.text.toString().length != 0){
            answer.text = answer.text.toString().subSequence(0, answer.text.toString().length - 1)
        }
    }
}