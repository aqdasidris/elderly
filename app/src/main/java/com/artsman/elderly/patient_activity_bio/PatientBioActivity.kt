package com.artsman.elderly.patient_activity_bio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.artsman.elderly.R
import com.artsman.elderly.care_taker.EventListFragment
import com.artsman.elderly.patient_info.PatientInfoFragment

class PatientBioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_bio)

        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder_1, PatientInfoFragment()).commit()


        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder_2, EventListFragment())
            .commit()
    }
}