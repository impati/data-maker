package com.example.impati.datamarker.domain

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class PathTest {

    @Test
    fun test() {
        assertThatCode {
            Path(
                value = "/abc/{id}/hello/{world}",
                pathVariable = listOf(
                    PathVariable(
                        type = DataType.STRING,
                        name = "id"
                    ),
                    PathVariable(
                        type = DataType.STRING,
                        name = "world"
                    )
                )
            )
        }.doesNotThrowAnyException()
    }

    @Test
    fun fail1() {
        assertThatCode {
            Path(
                value = "/abc/{id}/hello/{world}",
                pathVariable = listOf()
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun fail2() {
        assertThatCode {
            Path(
                value = "/abc/{id}/hello/{world}",
                pathVariable = listOf(
                    PathVariable(
                        type = DataType.STRING,
                        name = "id"
                    ),
                    PathVariable(
                        type = DataType.STRING,
                        name = "hello"
                    )
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun build() {
        val path = Path(
            value = "/abc/{id}/hello/{world}",
            pathVariable = listOf(
                PathVariable(
                    type = DataType.LONG,
                    name = "id",
                    domain = Domain(
                        type = DomainType.RANGE,
                        from = 1,
                        to = 1000
                    )
                ),
                PathVariable(
                    type = DataType.STRING,
                    name = "world"
                )
            )
        )

        println(path.build())
    }
}
