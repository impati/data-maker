package com.example.impati.datamarker.domain

import org.springframework.stereotype.Repository

@Repository
class HttpSpecRepository {

    private val store: MutableMap<String, HttpSpec> = mutableMapOf()

    fun save(spec: HttpSpec): HttpSpec {
        store[spec.id] = spec
        return spec;
    }

    fun findById(id: String): HttpSpec {
        return store[id]!!
    }

    fun findAll(): List<HttpSpec> {
        return store.values.toList()
    }
}
