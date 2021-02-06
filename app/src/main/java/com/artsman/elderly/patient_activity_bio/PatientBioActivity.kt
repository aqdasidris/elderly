package com.artsman.elderly.patient_activity_bio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import com.artsman.elderly.R
import com.artsman.elderly.care_taker.EventListFragment
import com.artsman.elderly.patient_info.AssetPatientRepository
import com.artsman.elderly.patient_info.PatientInfoFragment
import com.artsman.elderly.patient_info.PatientInfoRepository
import com.artsman.elderly.patient_info.api.PatientInfoAPI
import com.artsman.elderly.patient_list.PatientAdapter

class PatientBioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_bio)
        val tb= findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(tb)
        supportActionBar?.title = "Owais Idris"
        supportActionBar?.setDisplayShowTitleEnabled(true)


        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder_1, PatientInfoFragment()).commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, EventListFragment()).commit()

    }
}