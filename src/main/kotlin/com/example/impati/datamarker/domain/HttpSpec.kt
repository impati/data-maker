package com.example.impati.datamarker.domain

import com.example.impati.datamarker.domain.encoder.BasicEncoder
import java.util.*

data class HttpSpec(
    val url: String,
    val path: String,
    val method: HttpMethod,
    val header: List<Header>,
    val queryParam: List<QueryParam>,
    val properties: List<Property>,
    val name: String,
    val id: String = UUID.randomUUID().toString().substring(0, 5),
) {

    fun payload(): String? {
        if (properties.isEmpty()) {
            return null
        }

        return BasicEncoder.propertiesEncode(properties)
    }
}
