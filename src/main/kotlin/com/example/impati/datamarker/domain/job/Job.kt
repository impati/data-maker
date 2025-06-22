package com.example.impati.datamarker.domain.job

import com.example.impati.datamarker.domain.HttpSpec
import java.util.*

class Job(
    val type: JobType,
    val duration: Long, // 단위는 초
    val tps: Tps,
    val httpSpec: HttpSpec,
    val jobId: String = UUID.randomUUID().toString().substring(0, 7),
) {

    fun isSingle(): Boolean {
        return type == JobType.SINGLE
    }

    companion object {

        fun single(httpSpec: HttpSpec): Job {
            return Job(
                type = JobType.SINGLE,
                duration = 1,
                tps = Tps(1),
                httpSpec
            )
        }
    }
}
