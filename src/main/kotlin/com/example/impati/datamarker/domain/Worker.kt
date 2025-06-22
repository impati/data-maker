package com.example.impati.datamarker.domain

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.scheduler.Schedulers

@Component
class Worker(
    private val webClientBuilder: WebClient.Builder
) {

    private val clientCache = mutableMapOf<String, WebClient>()

    fun work(httpRequest: HttpRequest) {
        val requestSpec = buildRequestSpec(httpRequest)

        requestSpec
            .retrieve()
            .bodyToMono(String::class.java)
            .doOnNext { println("next $it") }
            .subscribe()
    }

    fun workSync(httpRequest: HttpRequest): Map<String, Any> {
        val requestSpec = buildRequestSpec(httpRequest)

        return requestSpec
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<Map<String, Any>>() {})
            .subscribeOn(Schedulers.boundedElastic())
            .block() ?: emptyMap()
    }

    private fun buildRequestSpec(httpRequest: HttpRequest): WebClient.RequestHeadersSpec<*> {
        val webClient = clientCache.getOrPut(httpRequest.url) {
            webClientBuilder
                .baseUrl(httpRequest.url)
                .build()
        }

        val queryParams: MultiValueMap<String, String> = LinkedMultiValueMap<String, String>().apply {
            httpRequest.queryParams.forEach {
                add(it.key, it.value)
            }
        }

        val baseRequest = webClient
            .method(HttpMethod.valueOf(httpRequest.method.name))
            .uri { uriBuilder ->
                uriBuilder
                    .path(httpRequest.path)
                    .queryParams(queryParams)
                    .build()
            }
            .headers { headers -> httpRequest.headers.forEach { headers[it.key] = it.value } }

        return httpRequest.payload?.let { payloadValue -> baseRequest.bodyValue(payloadValue) } ?: baseRequest
    }
}
