package com.example.impati.datamarker.domain

data class Header(
    val key: String,
    val value: String
) {

    companion object {

        fun applicationJson(): Header {
            return Header(
                "Content-Type",
                "application/json"
            )
        }
    }
}
