package com.example.impati.datamarker.api

import com.example.impati.datamarker.domain.HttpSpecRepository
import com.example.impati.datamarker.domain.job.Job
import com.example.impati.datamarker.domain.job.JobAgent
import com.example.impati.datamarker.domain.job.JobRepository
import com.example.impati.datamarker.domain.job.Tps
import com.example.impati.datamarker.domain.limiter.MemoryQueueLimiter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class JobController(
    private val jobRepository: JobRepository,
    private val specRepository: HttpSpecRepository,
    private val jobAgent: JobAgent,
    private val memoryQueueLimiter: MemoryQueueLimiter
) {

    @PostMapping("/jobs")
    fun create(@RequestBody request: JobRequest): ResponseEntity<Job> {
        val httpSpec = specRepository.findById(request.specId)
        val job = jobRepository.save(
            Job(
                request.type,
                request.duration,
                Tps(request.tps),
                httpSpec
            )
        )

        return ResponseEntity.ok(job)
    }

    @PutMapping("/jobs/{jobId}")
    fun edit(@PathVariable jobId: String, @RequestBody request: JobRequest): ResponseEntity<Job> {
        val httpSpec = specRepository.findById(request.specId)
        val job = jobRepository.save(
            Job(
                request.type,
                request.duration,
                Tps(request.tps),
                httpSpec,
                jobId
            )
        )

        return ResponseEntity.ok(job)
    }

    @GetMapping("/jobs")
    fun list(): ResponseEntity<List<Job>> {
        return ResponseEntity.ok(jobRepository.findAll())
    }

    @PostMapping("/jobs/{jobId}/execution")
    fun execute(@PathVariable jobId: String): ResponseEntity<String> {
        jobAgent.run(jobId)

        return ResponseEntity.ok("done")
    }

    @PostMapping("/jobs/{jobId}/executionMemoryQueueLimiter")
    fun executeMemoryQueueLimiter(@PathVariable jobId: String): ResponseEntity<String> {
        memoryQueueLimiter.run(jobId)

        return ResponseEntity.ok("done")
    }
}
