package com.example.impati.datamarker.domain.generator

import com.example.impati.datamarker.domain.DataType
import com.example.impati.datamarker.domain.Domain

object ValueGeneratorResolver : ValueGenerator {

    private val generators: Map<DataType, ValueGenerator> = mapOf(
        Pair(DataType.STRING, StringValueGenerator()),
        Pair(DataType.LONG, LongValueGenerator()),
        Pair(DataType.DATE, DateValueGenerator()),
    )

    override fun generate(type: DataType, domain: Domain?): Any {
        return generators[type]!!.generate(type, domain)
    }
}
