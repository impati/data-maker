package com.example.impati.datamarker.domain.job

import com.example.impati.datamarker.domain.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JobAgentTest {

    @Autowired
    lateinit var jobAgent: JobAgent

    @Autowired
    lateinit var jobRepository: JobRepository


    @Test
    fun test() {
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
        val spec = HttpSpec(
            url = "http://localhost:8081/sellers",
            path = "",
            method = HttpMethod.POST,
            header = listOf(Header("Content-Type", "application/json")),
            queryParam = listOf(),
            properties = properties
        )
        val job = jobRepository.save(
            Job(
                duration = 1,
                tps = Tps(1),
                spec
            )
        )

        jobAgent.run(job.jobId)

        Thread.sleep(1000 * 60)
    }
}
