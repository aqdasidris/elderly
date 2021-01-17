package com.artsman.elderly

import com.artsman.elderly.care_taker.EventInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class GenericData<T> (val data: T) {

    override fun toString(): String {
        return data.toString()
    }

}