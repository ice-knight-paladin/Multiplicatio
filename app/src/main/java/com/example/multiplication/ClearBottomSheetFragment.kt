package com.example.multiplication

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ClearBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_clear) {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_yes).setOnClickListener {
            val repository =
                context?.let { it1 -> Core(it1).daomulti() }
                    ?.let { it2 -> Repository.Base(it2, Now.Base()) }
            CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).launch(Dispatchers.IO) {
                repository?.clear_table()
            }
            dismiss()
        }
        view.findViewById<Button>(R.id.btn_no).setOnClickListener {
            dismiss()
        }
    }
}