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
                    Variable(
                        type = DataType.STRING,
                        name = "id"
                    ),
                    Variable(
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
                    Variable(
                        type = DataType.STRING,
                        name = "id"
                    ),
                    Variable(
                        type = DataType.STRING,
                        name = "hello"
                    )
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
