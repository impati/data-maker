package com.example.impati.datamarker.domain

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WorkerTest {

    @Autowired
    lateinit var worker: Worker

    @Test
    fun call() {

        val properties = listOf(
            Property(
                type = DataType.STRING,
                name = "identification"
            ),
            Property(
                type = DataType.STRING,
                name = "password"
            ),
            Property(
                type = DataType.STRING,
                name = "sellerName"
            ),
            Property(
                type = DataType.STRING,
                name = "email",
                domain = Domain(
                    type = DomainType.EMAIL
                )
            ),
            Property(
                type = DataType.STRING,
                name = "account"
            )
        )

        worker.work(
            HttpRequest(
                url = "http://localhost:8081/sellers",
                path = "",
                method = HttpMethod.POST,
                headers = listOf(Header("Content-Type", "application/json")),
                queryParams = listOf(),
                payload = ""
            )
        )

        Thread.sleep(3000)
    }
}


