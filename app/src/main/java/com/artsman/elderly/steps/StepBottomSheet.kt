package com.artsman.elderly.steps

import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.artsman.elderly.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class StepBottomSheet:BottomSheetDialogFragment() {
    lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView=inflater.inflate(R.layout.add_step_layout,container,false)
        initialiseViews()
        return rootView
    }

    companion object{
        fun getInstance():StepBottomSheet{
            return StepBottomSheet()
        }
    }


    fun initialiseViews(){
        val txtStep=rootView.findViewById<EditText>(R.id.edt_step_goal)
        val btnSetGoal=rootView.findViewById<Button>(R.id.btn_add_step_goal)
//        txtStep.text
        btnSetGoal.setOnClickListener {
            val message=txtStep.text.toString()
            callBack?.invoke(StepInfo(step = message))
            dismiss()
        }


    }


    var callBack:((step:StepInfo)->Unit)?=null

    fun setStepCallBack(callback: ((step:StepInfo)->Unit)){
        this.callBack=callBack
    }

    data class StepInfo(val step: String)

}