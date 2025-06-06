package com.example.impati.datamarker.domain.job

import com.example.impati.datamarker.domain.HttpSpec
import java.util.*

class Job(
    val duration: Long, // 단위는 분
    val tps: Tps,
    val httpSpec: HttpSpec,
    val jobId: String = UUID.randomUUID().toString().substring(0, 5),
) {
}
