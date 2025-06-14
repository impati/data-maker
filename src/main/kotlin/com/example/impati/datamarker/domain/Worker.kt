package com.example.impati.datamarker.domain

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient

@Component
class Worker(
    private val webClientBuilder: WebClient.Builder
) {

    private val clientCache = mutableMapOf<String, WebClient>()

    fun work(spec: HttpSpec) {
        val webClient = clientCache.getOrPut(spec.url) {
            webClientBuilder
                .baseUrl(spec.url)
                .build()
        }

        val queryParams: MultiValueMap<String, String> = LinkedMultiValueMap<String, String>().apply {
            spec.queryParam.forEach {
                add(it.key, it.value)
            }
        }

        val baseRequest = webClient
            .method(HttpMethod.valueOf(spec.method.name))
            .uri { uriBuilder ->
                uriBuilder
                    .path(spec.path())
                    .queryParams(queryParams)
                    .build()
            }
            .headers { headers -> spec.header.forEach { headers[it.key] = it.value } }

        val requestSpec = spec.payload()?.let { payloadValue -> baseRequest.bodyValue(payloadValue) } ?: baseRequest

        requestSpec
            .retrieve()
            .bodyToMono(String::class.java)
            .doOnNext { println("next $it") }
            .subscribe()
    }
}
