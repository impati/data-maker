package com.example.impati.datamarker.domain

import com.example.impati.datamarker.domain.encoder.BasicEncoder
import com.example.impati.datamarker.domain.generator.ValueGeneratorResolver

class Property(
    val type: DataType,
    val name: String,
    val domain: Domain? = null,
    val properties: List<Property> = mutableListOf()
) {


    fun build(): String {
        if (type == DataType.OBJECT) {
            return BasicEncoder.keyEncode(name) + BasicEncoder.objectEncode(properties.map { it.build() })
        }

        return BasicEncoder.keyEncode(name) + BasicEncoder.valueEncode(ValueGeneratorResolver.generate(type, domain))
    }
}
