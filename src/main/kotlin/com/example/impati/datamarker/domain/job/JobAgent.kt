package com.example.impati.datamarker.domain.job

import com.example.impati.datamarker.domain.HttpRequestBuilder
import com.example.impati.datamarker.domain.Worker
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.concurrent.CountDownLatch

@Component
class JobAgent(
    private val jobRepository: JobRepository,
    private val httpRequestBuilder: HttpRequestBuilder,
    private val worker: Worker
) {
    fun run(jobId: String) {
        val latch = CountDownLatch(1)

        val job = jobRepository.findById(jobId)
        val httpRequest = httpRequestBuilder.build(job.httpSpec)
        Flux.interval(Duration.ZERO, Duration.ofMillis(1000 / job.tps.value))
            .take(Duration.ofSeconds(job.duration))
            .doOnNext {
                worker.work(httpRequest)
            }
            .doOnTerminate { latch.countDown() }
            .subscribe()

        latch.await()
    }


    fun supply(job: Job, context: Map<String, Any>): Map<String, Any> {
        val httpRequest = httpRequestBuilder.build(job.httpSpec, context)
        println("httpRequest = $httpRequest")
        return worker.workSync(httpRequest)
    }
}
