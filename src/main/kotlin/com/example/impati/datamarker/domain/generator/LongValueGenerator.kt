package com.example.impati.datamarker.domain.generator

import com.example.impati.datamarker.domain.DataType
import com.example.impati.datamarker.domain.Domain
import com.example.impati.datamarker.domain.DomainType
import java.util.*
import java.util.stream.IntStream

class LongValueGenerator : ValueGenerator {

    override fun generate(type: DataType, domain: Domain?): Any {
        if (domain == null) {
            return Random().nextLong()
        }
        if (domain.multiple) {
            return IntStream.range(1, 2.coerceAtLeast(Random().nextInt(5)))
                .mapToObj { generate(domain) }
                .toList()
        }

        return generate(domain)
    }

    private fun generate(domain: Domain): Long {
        return when (domain.type) {
            DomainType.RANGE -> {
                Random().nextLong(domain.from!!.toLong(), domain.to!!.toLong())
            }

            else -> {
                Random().nextLong()
            }
        }
    }
}
