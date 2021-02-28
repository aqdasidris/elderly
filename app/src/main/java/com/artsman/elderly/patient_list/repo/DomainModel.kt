package com.artsman.elderly.patient_list.repo

open class AbstractPatient<T:Map<String,Any>>(
    val id:Int,
    val name:String,
    val photoUrl:String
){
    companion object{
        fun AbstractPatient<Map<String,Any>>.toDBPatient():DBPatient{
            return DBPatient(id = this.id,patient_name = this.name,photoUrl = this.photoUrl)
        }
    }
}