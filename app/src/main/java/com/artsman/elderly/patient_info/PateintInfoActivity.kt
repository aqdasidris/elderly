package com.artsman.elderly.patient_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.artsman.elderly.R

class PateintInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pateint_info)
        supportFragmentManager.beginTransaction().replace(
            R.id.placeholder,
            PatientInfoFragment()
        ).commit()
    }
}