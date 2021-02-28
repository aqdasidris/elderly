package com.artsman.elderly.patient_list.repo

open class UIPatient(
    val id:Int,
    val patient_name:String,
    val photoUrl:String
){
    companion object{
        fun UIPatient.toDBPatient():DBPatient{
            return DBPatient(
                id = this.id,
                patient_name = this.patient_name,
                photoUrl = this.photoUrl

            )
        }
    }
}