package com.example.impati.datamarker.domain

data class Variable(
    val type: DataType,
    val name: String,
    val domain: Domain? = null
)
