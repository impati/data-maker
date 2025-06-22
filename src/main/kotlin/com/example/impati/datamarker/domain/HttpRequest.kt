package com.example.impati.datamarker.domain

data class HttpRequest(
    val url: String,
    val path: String,
    val method: HttpMethod,
    val headers: List<Header>,
    val queryParams: List<QueryParam>,
    val payload: String?
) {
}
