package com.example.impati.datamarker.domain.job

import com.example.impati.datamarker.domain.Worker
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.concurrent.CountDownLatch

@Component
class JobAgent(
    private val jobRepository: JobRepository,
    private val worker: Worker
) {
    fun run(jobId: String) {
        val latch = CountDownLatch(1)

        val job = jobRepository.findById(jobId)
        Flux.interval(Duration.ZERO, Duration.ofMillis(1000 / job.tps.value))
            .take(Duration.ofMinutes(job.duration))
            .doOnNext {
                worker.work(job.httpSpec)
            }
            .doOnTerminate { latch.countDown() }
            .subscribe()

        latch.await()
    }
}
