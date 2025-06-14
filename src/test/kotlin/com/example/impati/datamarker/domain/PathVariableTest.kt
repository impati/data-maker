package com.example.impati.datamarker.domain

import org.junit.jupiter.api.Test

class PathVariableTest {

    @Test
    fun test() {
        val pathVariable = PathVariable(
            type = DataType.LONG,
            name = "id"
        )

        println(pathVariable.build())
    }

    @Test
    fun test1() {
        val pathVariable = PathVariable(
            type = DataType.LONG,
            name = "id",
            domain = Domain(
                type = DomainType.RANGE,
                from = 1,
                to = 10_000_000
            )
        )

        println(pathVariable.build())
    }
}
