package com.artsman.elderly

data class GenericData<T> (val data: T) {

    override fun toString(): String {
        return data.toString()
    }

}