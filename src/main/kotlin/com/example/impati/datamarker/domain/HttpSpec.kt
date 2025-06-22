package com.example.impati.datamarker.domain

import com.example.impati.datamarker.domain.encoder.BasicEncoder
import java.util.*

data class HttpSpec(
    val url: String,
    val path: Path,
    val method: HttpMethod,
    val header: List<Header>,
    val queryParam: List<QueryParam>,
    val payload: Payload,
    val name: String,
    val id: String = UUID.randomUUID().toString().substring(0, 7),
) {

    fun payload(): String? {
        if (payload.properties.isEmpty()) {
            return null
        }

        return BasicEncoder.encodePayload(payload.properties)
    }
}
