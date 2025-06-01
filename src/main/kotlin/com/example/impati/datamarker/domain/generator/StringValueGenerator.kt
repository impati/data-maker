package com.example.impati.datamarker.domain.generator

import com.example.impati.datamarker.domain.DataType
import com.example.impati.datamarker.domain.Domain
import com.example.impati.datamarker.domain.DomainType
import java.util.*
import java.util.stream.IntStream

private const val MAX_REPEAT_COUNT = 5

class StringValueGenerator : ValueGenerator {

    override fun generate(type: DataType, domain: Domain?): Any {
        if (domain == null) {
            return randomString(1, 7)

        }
        if (domain.multiple) {
            return IntStream.range(1, Math.max(2, Random().nextInt(5)))
                .mapToObj { generate(domain) }
                .toList()
        }

        return generate(domain)
    }

    private fun generate(domain: Domain): String {
        return when (domain.type) {
            DomainType.ENUM -> {
                domain.values[Random().nextInt(domain.values.size)]
            }

            DomainType.EMAIL -> {
                val localPart = UUID.randomUUID().toString().substring(0, 5)
                val domains = listOf("example.com", "test.com", "mail.com")
                val chosenDomain = domains[Random().nextInt(domains.size)]
                "$localPart@$chosenDomain"
            }

            DomainType.IMAGE -> {
                val seed = UUID.randomUUID().toString()
                "https://picsum.photos/seed/$seed/200/200"
            }

            DomainType.RANGE -> {
                return randomString(domain.from!!, domain.to!!)
            }

            else -> {
                randomString(1, 7)
            }
        }
    }


    private fun randomString(minLength: Int, maxLength: Int): String {
        val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val length = Random().nextInt(minLength, maxLength + 1)
        return buildString {
            repeat(length) {
                append(alphabet.random())
            }
        }
    }
}
