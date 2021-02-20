package com.artsman.elderly.reminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artsman.elderly.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddReminderBottomSheet: BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reminder_config_layout, container,false)
    }
    companion object{
        fun getInstance(): AddReminderBottomSheet {
            return AddReminderBottomSheet()
        }
    }
}