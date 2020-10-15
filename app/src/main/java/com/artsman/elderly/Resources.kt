package com.artsman.elderly

data class Resources<T>(val data: T, val status: Status,  val error: String) {

    companion object{
        fun <T> success(data: T): Resources<T> {
            return Resources(data, Status.SUCCESS, "")
        }

        fun <T> loading(data: T): Resources<T> {
            return Resources(data, Status.LOADING, "")
        }

        fun <T> error(data: T): Resources<T> {
            return Resources(data, Status.FAILURE, "")
        }
    }
}

enum class Status{
    SUCCESS, LOADING, FAILURE
}