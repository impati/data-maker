package com.example.impati.datamarker.domain

data class Payload(val properties: List<Property>) {

    companion object {

        fun empty(): Payload {
            return Payload(listOf())
        }
    }

    fun isEmpty(): Boolean {
        return properties.isEmpty()
    }
}
