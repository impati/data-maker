package com.example.impati.datamarker.api

import com.example.impati.datamarker.domain.HttpRequestBuilder
import com.example.impati.datamarker.domain.HttpSpec
import com.example.impati.datamarker.domain.HttpSpecRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SpecController(
    private val httpSpecRepository: HttpSpecRepository,
    private val httpRequestBuilder: HttpRequestBuilder
) {
    @PostMapping("/spec")
    fun create(@RequestBody httpSpec: HttpSpec): ResponseEntity<HttpSpec> {
        return ResponseEntity.ok(httpSpecRepository.save(httpSpec))
    }

    @GetMapping("/spec")
    fun list(): ResponseEntity<List<HttpSpec>> {

        return ResponseEntity.ok(httpSpecRepository.findAll())
    }

    @GetMapping("/spec/{specId}/payload")
    fun buildPayload(@PathVariable specId: String): ResponseEntity<String> {

        val httpSpec = httpSpecRepository.findById(specId)
        val request = httpRequestBuilder.build(httpSpec)
        return ResponseEntity.ok(request.payload)
    }
}
