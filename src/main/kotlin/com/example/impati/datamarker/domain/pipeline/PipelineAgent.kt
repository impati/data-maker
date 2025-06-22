package com.example.impati.datamarker.domain.pipeline

import com.example.impati.datamarker.domain.job.JobAgent
import com.example.impati.datamarker.domain.job.JobRepository
import org.springframework.stereotype.Component

@Component
class PipelineAgent(
    private val pipelineRepository: PipelineRepository,
    private val jobRepository: JobRepository,
    private val jobAgent: JobAgent
) {

    fun run(id: String) {
        val pipeline = pipelineRepository.findById(id)

        var context = mapOf<String, Any>()

        for (step in pipeline.steps) {
            val job = jobRepository.findById(step.jobId)
            if (job.isSingle()) {
                context = jobAgent.supply(job, context)
                println("context = $context")
            } else {
                jobAgent.run(job.jobId)
            }
        }
    }
}
