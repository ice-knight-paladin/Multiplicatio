package com.example.multiplication

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.multiplication.Option.divmax
import com.example.multiplication.Option.divmin
import com.example.multiplication.Option.minusmax
import com.example.multiplication.Option.minusmin
import com.example.multiplication.Option.multimax
import com.example.multiplication.Option.multimin
import com.example.multiplication.Option.plusmax
import com.example.multiplication.Option.plusmin
import com.google.android.material.internal.ViewUtils.hideKeyboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.w3c.dom.Text


class OptionStartSheetDialogFragment : DialogFragment(R.layout.fragment_option) {

    private lateinit var minmulti: EditText
    private lateinit var maxmulti: EditText
    private lateinit var mindiv: EditText
    private lateinit var maxdiv: EditText

    private lateinit var minplus: EditText
    private lateinit var maxplus: EditText
    private lateinit var minminus: EditText
    private lateinit var maxminus: EditText

    private lateinit var cheak_view: TextView
    private var repositorySave: Repository.BaseSave? = null
    private var numbers = listOf<String>()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        if (dialog.window != null) {
            dialog.window!!.setGravity(Gravity.TOP)
            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation

            val params = dialog.window!!.attributes
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.MATCH_PARENT
            params.horizontalMargin = 0.05f
            params.verticalMargin = 0.05f
            dialog.window!!.attributes = params
        }
        dialog.getWindow()?.setBackgroundDrawableResource(R.drawable.dialog_option_background);

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false);

        val btn_close = view.findViewById<TextView>(R.id.btn_close_option)
        btn_close.setOnClickListener {
            dismiss()
        }
        repositorySave = context?.let { Core(it).daosave() }
            ?.let { Repository.BaseSave(it, Now.Base()) }

        minmulti = view.findViewById<EditText>(R.id.editTextMinMulti)
        maxmulti = view.findViewById<EditText>(R.id.editTextMaxMulti)
        mindiv = view.findViewById<EditText>(R.id.editTextMinDiv)
        maxdiv = view.findViewById<EditText>(R.id.editTextMaxDiv)

        minplus = view.findViewById<EditText>(R.id.editTextMinPlus)
        maxplus = view.findViewById<EditText>(R.id.editTextMaxPlus)
        minminus = view.findViewById<EditText>(R.id.editTextMinMinus)
        maxminus = view.findViewById<EditText>(R.id.editTextMaxMinus)

        cheak_view = view.findViewById(R.id.checksave)


        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
            numbers = listOf(
                repositorySave?.item(multimin)?.number.toString(),
                repositorySave?.item(multimax)?.number.toString(),
                repositorySave?.item(divmin)?.number.toString(),
                repositorySave?.item(divmax)?.number.toString(),

                repositorySave?.item(plusmin)?.number.toString(),
                repositorySave?.item(plusmax)?.number.toString(),
                repositorySave?.item(minusmin)?.number.toString(),
                repositorySave?.item(minusmax)?.number.toString(),
                )
            minmulti.setText(numbers[0])
            maxmulti.setText(numbers[1])
            mindiv.setText(numbers[2])
            maxdiv.setText(numbers[3])

            minplus.setText(numbers[4])
            maxplus.setText(numbers[5])
            minminus.setText(numbers[6])
            maxminus.setText(numbers[7])

            if (repositorySave?.item(multimin) == null) {
                repositorySave?.add(multimin, 1)
                minmulti.setText(repositorySave?.item(multimin)?.number.toString())
            }
            if (repositorySave?.item(multimax) == null) {
                repositorySave?.add(multimax, 9)
                maxmulti.setText(repositorySave?.item(multimax)?.number.toString())
            }
            if (repositorySave?.item(divmin) == null) {
                repositorySave?.add(divmin, 1)
                mindiv.setText(repositorySave?.item(divmin)?.number.toString())
            }
            if (repositorySave?.item(divmax) == null) {
                repositorySave?.add(divmax, 9)
                maxdiv.setText(repositorySave?.item(divmax)?.number.toString())
            }

            if (repositorySave?.item(plusmin) == null) {
                repositorySave?.add(plusmin, 1)
                minmulti.setText(repositorySave?.item(plusmin)?.number.toString())
            }
            if (repositorySave?.item(plusmax) == null) {
                repositorySave?.add(plusmax, 100)
                maxmulti.setText(repositorySave?.item(plusmax)?.number.toString())
            }
            if (repositorySave?.item(minusmin) == null) {
                repositorySave?.add(minusmin, 1)
                mindiv.setText(repositorySave?.item(minusmin)?.number.toString())
            }
            if (repositorySave?.item(minusmax) == null) {
                repositorySave?.add(minusmax, 100)
                maxdiv.setText(repositorySave?.item(minusmax)?.number.toString())
            }
        }

        view.findViewById<TextView>(R.id.btn_save_option).setOnClickListener {
            if (check()) {
                save()
                hideKeyboard(view)
            }
        }

        view.findViewById<TextView>(R.id.btn_cancel).setOnClickListener {
            minmulti.setText(numbers[0])
            maxmulti.setText(numbers[1])
            mindiv.setText(numbers[2])
            maxdiv.setText(numbers[3])

            minplus.setText(numbers[4])
            maxplus.setText(numbers[5])
            minminus.setText(numbers[6])
            maxminus.setText(numbers[7])
        }

        view.findViewById<TextView>(R.id.show_static).setOnClickListener{
            startActivity(Intent(context, StaticsBD::class.java))
            dismiss()
        }

        view.findViewById<TextView>(R.id.clear_static).setOnClickListener{
            ClearBottomSheetFragment().show(parentFragmentManager, "createClearFragment")
        }
    }


    private fun save() {
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
            repositorySave!!.update(minmulti.text.toString().toInt(), multimin)
            repositorySave!!.update(maxmulti.text.toString().toInt(), multimax)
            repositorySave!!.update(mindiv.text.toString().toInt(), divmin)
            repositorySave!!.update(maxdiv.text.toString().toInt(), divmax)

            repositorySave!!.update(minplus.text.toString().toInt(), plusmin)
            repositorySave!!.update(maxplus.text.toString().toInt(), plusmax)
            repositorySave!!.update(minminus.text.toString().toInt(), minusmin)
            repositorySave!!.update(maxminus.text.toString().toInt(), minusmax)
        }

    }


    fun check(): Boolean {
        if (minmulti.text.toString().toInt() >= maxmulti.text.toString().toInt() ||
            mindiv.text.toString().toInt() >= maxdiv.text.toString().toInt() ||
            minplus.text.toString().toInt() >= maxplus.text.toString().toInt() ||
            minminus.text.toString().toInt() >= maxminus.text.toString().toInt()
        ) {
            cheak_view.visibility = View.VISIBLE
            cheak_view.text = "минимальное число должно быть больше максимального"
            cheak_view.postDelayed(
                {
                    cheak_view.visibility = View.GONE
                },
                2000
            )
            return false
        }
        if (minmulti.text.toString().toInt() == 0 || mindiv.text.toString().toInt() == 0 ||
            minplus.text.toString().toInt() == 0 || minminus.text.toString().toInt() == 0 ||
            minmulti.text.toString().isEmpty() || maxmulti.text.toString().isEmpty() ||
            mindiv.text.toString().isEmpty() || maxdiv.text.toString().isEmpty() ||
            minplus.text.toString().isEmpty() || maxplus.text.toString().isEmpty() ||
            minminus.text.toString().isEmpty() || maxminus.text.toString().isEmpty()
            ){
            cheak_view.visibility = View.VISIBLE
            cheak_view.text = "минимальное число должно быть не равно 0"
            cheak_view.postDelayed(
                {
                    cheak_view.visibility = View.GONE
                },
                2000
            )
            return false
        }
        return true
    }

}
