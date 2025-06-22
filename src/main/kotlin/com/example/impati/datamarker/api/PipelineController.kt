package com.example.impati.datamarker.api

import com.example.impati.datamarker.domain.pipeline.Pipeline
import com.example.impati.datamarker.domain.pipeline.PipelineAgent
import com.example.impati.datamarker.domain.pipeline.PipelineRepository
import com.example.impati.datamarker.domain.pipeline.Step
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PipelineController(
    private val pipelineAgent: PipelineAgent,
    private val pipelineRepository: PipelineRepository
) {

    @PostMapping("/pipeline")
    fun create(@RequestBody request: PipelineRequest): ResponseEntity<Pipeline> {
        return ResponseEntity.ok(
            pipelineRepository.save(
                Pipeline(
                    request.steps.map { Step(it.jobId) }.toList(),
                    request.name
                )
            )
        )
    }

    @GetMapping("/pipeline/{id}")
    fun get(@PathVariable id: String): ResponseEntity<Pipeline> {
        return ResponseEntity.ok(pipelineRepository.findById(id))
    }


    @GetMapping("/pipeline")
    fun list(): ResponseEntity<List<Pipeline>> {
        return ResponseEntity.ok(pipelineRepository.findAll())
    }


    @PostMapping("/pipeline/{id}/execution")
    fun execute(@PathVariable id: String): ResponseEntity<String> {
        pipelineAgent.run(id)

        return ResponseEntity.ok("done")
    }

}
