package com.artsman.elderly.events

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.artsman.elderly.R

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        supportFragmentManager.beginTransaction().replace(
            R.id.event_placeholder,
            AddEventFragment()
        ).commit()

    }
}