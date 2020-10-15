package com.artsman.elderly

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.artsman.elderly.auth.GuardianRegistrationFragment
import com.artsman.elderly.auth.ICanHandleBackPress
import com.artsman.elderly.events.EventActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(
            R.id.placeholder,
            GuardianRegistrationFragment()
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