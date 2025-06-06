package com.example.impati.datamarker.api

import com.example.impati.datamarker.domain.HttpSpec
import com.example.impati.datamarker.domain.HttpSpecRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
}
