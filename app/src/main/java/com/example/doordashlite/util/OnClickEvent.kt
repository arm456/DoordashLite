package com.example.doordashlite.util

class OnClickEvent<T>(private val id: Int) {
    
    var receivedData = false
        private set

    fun getDataIfReceived(): Int? {
        return if (receivedData) {
            null
        } else {
            receivedData = true
            id
        }
    }
}