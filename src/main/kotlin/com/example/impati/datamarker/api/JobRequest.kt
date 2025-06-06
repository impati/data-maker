package com.example.impati.datamarker.api

data class JobRequest(
    val duration: Long,
    val tps: Long,
    val specId: String
)
