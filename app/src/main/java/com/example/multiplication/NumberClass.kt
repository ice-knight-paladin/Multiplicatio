package com.example.multiplication

import android.util.Log
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
    abstract fun show(answer: TextView, expression: TextView)
    abstract fun show_delete(answer: TextView)
    abstract fun Item(b: Boolean, repository: Repository)

}

class NumberDiv(start: Int, end: Int) : NumberClass(start, end) {
    private var div = number_one

    override fun ischeak(answer: String):Boolean {
        return answer.length == div.toString().length && answer.toInt() == div
    }

    override fun random(){
        super.random()
        div = number_one
    }


    override fun Item(i: Boolean, repository: Repository) {
        repository as Repository.BaseDiv
        if (repository.item("${number_one*number_two} / ${number_two}") == null) {
            if (i)
                repository.add("${number_one*number_two} / ${number_two}", 1, 0)
            else
                repository.add("${number_one*number_two} / ${number_two}", 0, 1)
        } else {
            repository.update("${number_one*number_two} / ${number_two}", i)
        }
    }

    override fun show(answer: TextView, expression:TextView){
        random()
        answer.text = ""
        expression.text = "${number_one * number_two} / $number_two="
    }

    override fun show_delete(answer: TextView){
        if (answer.text.toString().length != 0){
            answer.text = answer.text.toString().subSequence(0, answer.text.toString().length - 1)
        }
    }
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


    override fun Item(i: Boolean, repository: Repository) {
        repository as Repository.BaseMulti
        if     (repository.item("${min(number_one, number_two)} * ${max(number_one, number_two)}") == null) {
            if (i)
                repository.add("${min(number_one, number_two)} * ${max(number_one, number_two)}", 1, 0)
            else
                repository.add("${min(number_one, number_two)} * ${max(number_one, number_two)}", 0, 1)
        } else {
              repository.update("${min(number_one, number_two)} * ${max(number_one, number_two)}", i)
        }
    }

    override fun show(answer: TextView, expression:TextView){
        random()
        answer.text = ""
        expression.text = "$number_one * $number_two="
    }

    override fun show_delete(answer: TextView){
        if (answer.text.toString().length != 0){
            answer.text = answer.text.toString().subSequence(0, answer.text.toString().length - 1)
        }
    }
}

class NumberPlus(start: Int, end: Int) : NumberClass(start, end) {
    private var plus = number_one + number_two

    override fun ischeak(answer: String): Boolean {
        return answer.length == plus.toString().length && answer.toInt() == plus
    }

    override fun random() {
        super.random()
        plus = number_one + number_two
    }


    override fun Item(i: Boolean, repository: Repository) {
        repository as Repository.BasePlus
        if (repository.item("${min(number_one, number_two)} + ${max(number_one, number_two)}") == null) {
            if (i)
                repository.add(
                    "${min(number_one, number_two)} + ${max(number_one, number_two)}",
                    1,
                    0
                )
            else
                repository.add(
                    "${min(number_one, number_two)} + ${max(number_one, number_two)}",
                    0,
                    1
                )
        } else {
            repository.update("${min(number_one, number_two)} + ${max(number_one, number_two)}", i)
        }
    }

    override fun show(answer: TextView, expression: TextView) {
        random()
        answer.text = ""
        expression.text = "$number_one + $number_two="
    }

    override fun show_delete(answer: TextView) {
        if (answer.text.toString().length != 0) {
            answer.text = answer.text.toString().subSequence(0, answer.text.toString().length - 1)
        }
    }
}

class NumberMinus(private val start: Int,private val  end: Int) : NumberClass(start, end) {
    init {
        number_one = (number_two..end).random()
    }
    private var minus = number_one - number_two

    override fun ischeak(answer: String): Boolean {
        return answer.length == minus.toString().length && answer.toInt() == minus
    }

    override fun random() {
        super.random()
        number_one = (number_two..end).random()
        minus = number_one - number_two
    }


    override fun Item(i: Boolean, repository: Repository) {
        repository as Repository.BaseMinus
        if (repository.item(
                "${max(number_one, number_two)} - ${
                    min(
                        number_one,
                        number_two
                    )
                }"
            ) == null
        ) {
            if (i)
                repository.add(
                    "${max(number_one, number_two)} - ${min(number_one, number_two)}",
                    1,
                    0
                )
            else
                repository.add(
                    "${max(number_one, number_two)} - ${min(number_one, number_two)}",
                    0,
                    1
                )
        } else {
            repository.update("${max(number_one, number_two)} - ${min(number_one, number_two)}", i)
        }
    }

    override fun show(answer: TextView, expression: TextView) {
        random()
        Log.d("mylog", "${number_one}, ${number_two}")
        answer.text = ""
        expression.text = "$number_one - $number_two="
    }

    override fun show_delete(answer: TextView) {
        if (answer.text.toString().length != 0) {
            answer.text = answer.text.toString().subSequence(0, answer.text.toString().length - 1)
        }
    }
}