package com.example.impati.datamarker.domain.pipeline

import org.springframework.stereotype.Repository

@Repository
class PipelineRepository {

    private val store: MutableMap<String, Pipeline> = mutableMapOf()

    fun save(pipeline: Pipeline): Pipeline {
        store[pipeline.id] = pipeline
        return pipeline
    }

    fun findById(id: String): Pipeline {
        return store[id]!!
    }

    fun findAll(): List<Pipeline> {
        return store.values.toList()
    }
}
