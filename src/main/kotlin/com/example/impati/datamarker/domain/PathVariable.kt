package com.example.impati.datamarker.domain

import com.example.impati.datamarker.domain.generator.ValueGeneratorResolver

data class PathVariable(
    val type: DataType,
    val name: String,
    val domain: Domain? = null
) {

    fun build(): String {
        return ValueGeneratorResolver.generate(type, domain).toString()
    }
}
