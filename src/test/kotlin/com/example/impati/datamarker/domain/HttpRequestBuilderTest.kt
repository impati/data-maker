package com.example.impati.datamarker.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HttpRequestBuilderTest {

    private var sut = HttpRequestBuilder()

    @Test
    fun `httpSpec 으로 전송 가능한 httpRequest 를 생성한다`() {
        val httpSpec = HttpSpec(
            url = "http://localhost:8080",
            path = Path(
                "/sellers/{sellerId}",
                listOf(
                    Variable(
                        type = DataType.LONG,
                        name = "sellerId",
                        domain = Domain.range(1, 10)
                    )
                )
            ),
            method = HttpMethod.GET,
            header = listOf(Header.applicationJson()),
            queryParam = listOf(),
            payload = Payload.empty(),
            name = "셀러 조회"
        )


        val httpRequest = sut.build(httpSpec)

        assertThat(httpRequest.url).isEqualTo("http://localhost:8080")
        assertThat(httpRequest.path).containsPattern("^/sellers/([1-9]|10)\$")
    }

    @Test
    fun `httpSpec 으로 전송 가능한 httpRequest 를 생성한다2`() {
        val context = mapOf(Pair("sellerId", 2323))
        val httpSpec = HttpSpec(
            url = "http://localhost:8080",
            path = Path(
                "/sellers/{sellerId}",
                listOf(
                    Variable(
                        type = DataType.LONG,
                        name = "sellerId",
                        domain = Domain.range(1, 10)
                    )
                )
            ),
            method = HttpMethod.GET,
            header = listOf(Header.applicationJson()),
            queryParam = listOf(),
            payload = Payload.empty(),
            name = "셀러 조회"
        )

        val httpRequest = sut.build(httpSpec, context)

        assertThat(httpRequest.url).isEqualTo("http://localhost:8080")
        assertThat(httpRequest.path).isEqualTo("/sellers/2323")
    }

    @Test
    fun `httpSpec 으로 전송 가능한 httpRequest 를 생성한다 - payload`() {
        val context = mapOf(Pair("name", "impati"))
        val httpSpec = HttpSpec(
            url = "http://localhost:8080",
            path = Path(
                "/sellers",
                listOf()
            ),
            method = HttpMethod.POST,
            header = listOf(Header.applicationJson()),
            queryParam = listOf(),
            payload = Payload(
                listOf(
                    Property(
                        type = DataType.OBJECT,
                        name = "detail",
                        properties = listOf(
                            Property(
                                type = DataType.STRING,
                                name = "notice",
                            ),
                            Property(
                                type = DataType.LONG,
                                name = "size",
                                domain = Domain.range(1, 10)
                            )
                        )
                    ),
                    Property(
                        type = DataType.LONG,
                        name = "price",
                        domain = Domain.range(1, 1000)
                    ),
                    Property(
                        type = DataType.STRING,
                        name = "name",
                    ),
                )
            ),
            name = "셀러 조회"
        )

        val httpRequest = sut.build(httpSpec, context)

        val jsonNode = ObjectMapper().readTree(httpRequest.payload)
        assertThat(jsonNode.get("price").asText()).containsPattern("^[0-9]+\$")
        assertThat(jsonNode.get("name").asText()).isEqualTo("impati")
        assertThat(jsonNode.get("detail").get("notice").asText()).isNotNull()
        assertThat(jsonNode.get("detail").get("size").asText()).containsPattern("^([1-9]|10)\$")
    }

    @Test
    fun `httpSpec 으로 전송 가능한 httpRequest 를 생성한다 - queryParams`() {
        val httpSpec = HttpSpec(
            url = "http://localhost:8080",
            path = Path(
                "/members",
                listOf()
            ),
            queryParam = listOf(
                QueryParam(
                    key = "token",
                    value = "{token}",
                    variable = Variable(
                        type = DataType.STRING,
                        name = "token"
                    )
                )
            ),
            method = HttpMethod.GET,
            header = listOf(Header.applicationJson()),
            payload = Payload.empty(),
            name = "셀러 조회"
        )

        val httpRequest = sut.build(httpSpec, mapOf(Pair("token", "hello")))

        assertThat(httpRequest.queryParams).hasSize(1)
        assertThat(httpRequest.queryParams[0].key).isEqualTo("token")
        assertThat(httpRequest.queryParams[0].value).isEqualTo("hello")
    }
}
