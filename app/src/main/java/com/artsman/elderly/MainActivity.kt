package com.artsman.elderly

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.artsman.elderly.auth.GuardianRegistrationFragment
import com.artsman.elderly.auth.ICanHandleBackPress
import com.artsman.elderly.care_taker.PatientListFragment
import com.artsman.elderly.events.EventActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.EOFException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        var s: String=""
        val data= applicationContext.assets.open("patient_info.txt").bufferedReader().use {
            it.readText()
        }
        s+=data
        Log.d("s",s)

        var e: String= ""
        val gData=applicationContext.assets.open("guardian_info.txt").bufferedReader().use {
            it.readText()
        }
        e+=gData
        Log.d("e",e)

        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(
            R.id.placeholder,
            PatientListFragment()
        ).commit()
    }




    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if(it is ICanHandleBackPress){
                if(!it.fragmentBackPress()){
                    super.onBackPressed()
                }
            }
        }

    }


}