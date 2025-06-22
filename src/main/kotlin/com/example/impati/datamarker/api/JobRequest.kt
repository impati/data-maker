package com.example.impati.datamarker.api

import com.example.impati.datamarker.domain.job.JobType

data class JobRequest(
    val type: JobType,
    val duration: Long,
    val tps: Long,
    val specId: String
)
