package com.example.impati.datamarker.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class QueryParamTest {


    @Test
    fun test() {
        val queryParam = QueryParam(
            "required",
            "false"
        )

        assertThat(queryParam.key).isEqualTo("required")
        assertThat(queryParam.value).isEqualTo("false")
    }

    @Test
    fun test1() {
        // ?hello={}
        val queryParam = QueryParam(
            "required",
            "{required}",
            Variable(
                type = DataType.STRING,
                name = "required",
                domain = Domain(
                    type = DomainType.ENUM,
                    values = listOf("true")
                )
            )
        )

        assertThat(queryParam.key).isEqualTo("required")
        assertThat(queryParam.value).isEqualTo("{required}")
    }

    @Test
    fun test2() {
        Assertions.assertThatCode {
            QueryParam(
                "required",
                "{required}"
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}

