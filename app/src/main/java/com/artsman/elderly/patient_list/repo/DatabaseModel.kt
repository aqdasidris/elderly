package com.artsman.elderly.patient_list.repo

import android.util.JsonReader
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artsman.elderly.patient_list.PatientListItem
import com.google.gson.Gson
import java.io.StringReader

@Entity(tableName = "patient_table")
data class DBPatient(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val patient_name:String,
    val photoUrl: String
){
    companion object {
        fun DBPatient.toUIPatient(): UIPatient{
            val uiPatient=UIPatient(
                this.id,
                this.patient_name,
                this.photoUrl
            )
            return uiPatient

        }
    }
}