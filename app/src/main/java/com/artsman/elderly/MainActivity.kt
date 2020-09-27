package com.artsman.elderly

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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