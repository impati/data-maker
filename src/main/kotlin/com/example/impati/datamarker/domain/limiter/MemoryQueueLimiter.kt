package com.example.impati.datamarker.domain.limiter

import com.example.impati.datamarker.domain.HttpRequest
import com.example.impati.datamarker.domain.HttpRequestBuilder
import com.example.impati.datamarker.domain.Worker
import com.example.impati.datamarker.domain.job.Job
import com.example.impati.datamarker.domain.job.JobRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.concurrent.BlockingQueue
import java.util.concurrent.CountDownLatch
import java.util.concurrent.LinkedBlockingQueue

@Component
class MemoryQueueLimiter(
    private val jobRepository: JobRepository,
    private val httpRequestBuilder: HttpRequestBuilder,
    private val worker: Worker,
    private val queue: BlockingQueue<JobTask> = LinkedBlockingQueue()
) {


    fun run(jobId: String) {
        val latch = CountDownLatch(1)

        val job = jobRepository.findById(jobId)
        Flux.interval(Duration.ZERO, Duration.ofMillis(1000 / job.tps.value))
            .take(Duration.ofSeconds(job.duration))
            .publishOn(Schedulers.boundedElastic())
            .doOnNext {
                queue.put(JobTask(job, httpRequestBuilder.build(job.httpSpec)))
            }
            .doOnTerminate { latch.countDown() }
            .subscribe()

        latch.await()
    }

    @Scheduled(fixedRate = 500)
    fun poll() {
        val jobTask = queue.poll() ?: return

        worker.work(jobTask.request)
    }

    data class JobTask(
        val job: Job,
        val request: HttpRequest
    )
}
