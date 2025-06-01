package com.example.impati.datamarker.domain.generator

import com.example.impati.datamarker.domain.DataType
import com.example.impati.datamarker.domain.Domain
import com.example.impati.datamarker.domain.DomainType
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class DateValueGenerator : ValueGenerator {

    override fun generate(type: DataType, domain: Domain?): Any {
        if (domain == null) {
            return randomDateTime(
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2050, 1, 1, 0, 0),
            )
        }

        return generate(domain)
    }

    private fun generate(domain: Domain): LocalDateTime {
        return when (domain.type) {
            DomainType.RANGE -> {
                randomDateTime(domain.start!!, domain.end!!)
            }

            else -> {
                randomDateTime(
                    LocalDateTime.of(2000, 1, 1, 0, 0),
                    LocalDateTime.of(2050, 1, 1, 0, 0),
                )
            }
        }
    }

    private fun randomDateTime(
        startInclusive: LocalDateTime,
        endExclusive: LocalDateTime
    ): LocalDateTime {
        // 1) start와 end를 UTC 기준 epochSecond로 변환
        val startEpoch = startInclusive.toEpochSecond(ZoneOffset.UTC)
        val endEpoch = endExclusive.toEpochSecond(ZoneOffset.UTC)

        // 2) 해당 epochSecond 구간에서 임의의 초(sec)를 뽑는다
        val randomEpoch = Random().nextLong(startEpoch, endEpoch)

        // 3) Instant → LocalDateTime으로 다시 변환 (UTC 기준)
        return LocalDateTime.ofEpochSecond(randomEpoch, 0, ZoneOffset.UTC)
    }
}
