package com.artsman.elderly.reminders

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.artsman.elderly.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class AddReminderBottomSheet : BottomSheetDialogFragment() {
    lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView= inflater.inflate(R.layout.reminder_config_layout, container, false)

        initialiseViews()
        return rootView
    }

    companion object {
        fun getInstance(): AddReminderBottomSheet {
            return AddReminderBottomSheet()
        }
    }



    var mDateTime: Calendar?= null
    var mDateView: Button?= null
    private fun initialiseViews() {
        val dateText = rootView.findViewById<EditText>(R.id.edt_reminder_title)
        mDateView = rootView.findViewById<Button>(R.id.btn_date)

        mDateView?.setOnClickListener {
            val c = Calendar.getInstance()

            val m = c.get(Calendar.MONTH)
            val d = c.get(Calendar.DAY_OF_MONTH)
            val y = c.get(Calendar.YEAR)
            val date = DatePickerDialog(
                requireActivity(),
                { view, year, month, day->
                    saveDate(year, month, day)
                    updateDateOnView()
                    showTimePicker()

                }, y, m, d
            )

            date.show()

        }
        val btnSave= rootView.findViewById<Button>(R.id.btn_add_reminder)
        btnSave.setOnClickListener {
            mDateTime?.let {
                val millis= it.timeInMillis
                val message= dateText.text.toString()
                callback?.invoke(ReminderInfo(dateTime= millis, message = message))
                dismiss()
            }
        }
    }

    private fun updateDateOnView() {
        mDateTime?.let {
            val day= it.get(Calendar.DAY_OF_MONTH)
            val dayOfWeek= it.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            val month= it.get(Calendar.MONTH)+1
            val monthName= it.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
            val year= it.get(Calendar.YEAR)
            val hourOfDay= it.get(Calendar.HOUR_OF_DAY)
            val minute= it.get(Calendar.MINUTE)
            mDateView?.setText("$dayOfWeek $day $monthName $year at $hourOfDay:$minute")
        }
    }

    private fun saveDate(year: Int, month: Int, day: Int) {
        mDateTime = Calendar.getInstance().apply {
            this.set(Calendar.YEAR, year)
            this.set(Calendar.MONTH, month)
            this.set(Calendar.DAY_OF_MONTH, day)
        }
    }

    private fun showTimePicker() {
        mDateTime?.let { cal ->
            val timelistener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                saveTime(hourOfDay, minute)
                updateDateOnView()
            }
            TimePickerDialog(
                requireContext(),
                timelistener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    private fun saveTime(hourOfDay: Int, minute: Int) {
        mDateTime?.set(Calendar.HOUR_OF_DAY, hourOfDay)
        mDateTime?.set(Calendar.MINUTE, minute)
    }

    var callback: ((info: ReminderInfo)->Unit)?= null

    fun setReminderInputCompleteCallback(callback: ((info: ReminderInfo)->Unit)){
        this.callback= callback
    }

    data class ReminderInfo(val message: String, val dateTime: Long)
}






