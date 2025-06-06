package com.example.impati.datamarker.domain.job

import org.springframework.stereotype.Repository

@Repository
class JobRepository {

    private val store: MutableMap<String, Job> = mutableMapOf()

    fun save(job: Job): Job {
        store[job.jobId] = job
        return job;
    }

    fun findById(id: String): Job {
        return store[id]!!
    }

    fun findAll(): List<Job> {
        return store.values.toList()
    }
}
