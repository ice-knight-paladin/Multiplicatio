package com.example.multiplication

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class ClearBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_clear) {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.btn_yes).setOnClickListener {
            val repository_multi =
                context?.let { it1 -> Core(it1).daomulti() }
                    ?.let { it2 -> Repository.BaseMulti(it2, Now.Base()) }
            val repository_div =
                context?.let { it1 -> Core(it1).daodiv() }
                    ?.let { it2 -> Repository.BaseDiv(it2, Now.Base()) }

            val repository_plus =
                context?.let { it1 -> Core(it1).daoplus() }
                    ?.let { it2 -> Repository.BasePlus(it2, Now.Base()) }

            val repository_minus =
                context?.let { it1 -> Core(it1).daominus() }
                    ?.let { it2 -> Repository.BaseMinus(it2, Now.Base()) }

            CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
                repository_multi?.clear_table()
                repository_div?.clear_table()
                repository_plus?.clear_table()
                repository_minus?.clear_table()
            }
            dismiss()
        }
        view.findViewById<TextView>(R.id.btn_no).setOnClickListener {
            dismiss()
        }
    }
}