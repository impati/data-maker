package com.example.impati.datamarker.api

data class PipelineRequest(
    val name: String,
    val steps: List<StepRequest>
) {

    data class StepRequest(
        val jobId: String
    )
}
