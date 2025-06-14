package com.example.impati.datamarker.api

import com.example.impati.datamarker.domain.HttpSpec
import com.example.impati.datamarker.domain.HttpSpecRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SpecController(
    private val httpSpecRepository: HttpSpecRepository
) {
    @PostMapping("/spec")
    fun create(@RequestBody httpSpec: HttpSpec): ResponseEntity<HttpSpec> {
        return ResponseEntity.ok(httpSpecRepository.save(httpSpec))
    }

    @GetMapping("/spec")
    fun list(): ResponseEntity<List<HttpSpec>> {

        return ResponseEntity.ok(httpSpecRepository.findAll())
    }

    @GetMapping("/spec/{specId}")
    fun buildPayload(@PathVariable specId: String): ResponseEntity<String> {

        return ResponseEntity.ok(httpSpecRepository.findById(specId).payload())
    }
}
