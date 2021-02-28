package com.artsman.elderly.add_patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.artsman.elderly.R

class AddPatientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)
        supportFragmentManager.beginTransaction().replace(
            R.id.placeholder,
            AddPatientFragment()
        ).commit()
    }
}