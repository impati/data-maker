package com.example.impati.datamarker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DataMarkerApplication

fun main(args: Array<String>) {
    runApplication<DataMarkerApplication>(*args)
}
