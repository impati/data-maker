package com.example.impati.datamarker.domain

import com.example.impati.datamarker.domain.encoder.BasicEncoder
import com.example.impati.datamarker.domain.generator.ValueGeneratorResolver
import org.springframework.stereotype.Component
import java.util.*

@Component
class HttpRequestBuilder {

    fun build(spec: HttpSpec): HttpRequest {
        val url = spec.url
        val path = path(spec.path, emptyMap())
        val httpMethod = spec.method
        val headers = spec.header
        val queryParam = queryParam(spec.queryParam, emptyMap())
        val payload = payload(spec.payload, emptyMap())

        return HttpRequest(
            url,
            path,
            httpMethod,
            headers,
            queryParam,
            payload
        )
    }

    fun build(spec: HttpSpec, context: Map<String, Any>): HttpRequest {
        val url = spec.url
        val path = path(spec.path, context)
        val httpMethod = spec.method
        val headers = spec.header
        val queryParam = queryParam(spec.queryParam, context)
        val payload = payload(spec.payload, context)

        return HttpRequest(
            url,
            path,
            httpMethod,
            headers,
            queryParam,
            payload
        )
    }

    private fun path(path: Path, context: Map<String, Any>): String {
        if (path.isPathVariableEmpty()) {
            return path.value
        }

        var result = path.value
        for (variable in path.pathVariable) {
            val name = variable.name
            val value = if (context.containsKey(name)) context[name] else ValueGeneratorResolver.generate(
                variable.type,
                variable.domain
            )

            result = result.replace("{$name}", value.toString())
        }

        return result
    }

    private fun queryParam(queryParams: List<QueryParam>, context: Map<String, Any>): List<QueryParam> {

        val params = mutableListOf<QueryParam>()

        for (param in queryParams) {
            val name = param.key
            val value = if (context.containsKey(name)) context[name] else {
                if (Objects.isNull(param.variable)) {
                    param.value
                } else {
                    ValueGeneratorResolver.generate(
                        param.variable!!.type,
                        param.variable.domain
                    )
                }

            }
            params.add(QueryParam(name, value.toString()))
        }

        return params
    }

    private fun payload(payload: Payload, context: Map<String, Any>): String? {
        if (payload.isEmpty()) {
            return null
        }

        return BasicEncoder.objectEncode(payload.properties.map { buildPayload(it, context) }.toList())
    }

    private fun buildPayload(property: Property, context: Map<String, Any>): String {
        if (property.type == DataType.OBJECT) {
            return BasicEncoder.keyEncode(property.name) +
                    BasicEncoder.objectEncode(property.properties.map { buildPayload(it, context) })
        }

        val value = if (context.containsKey(property.name)) context[property.name] else ValueGeneratorResolver.generate(
            property.type,
            property.domain
        )

        return BasicEncoder.keyEncode(property.name) + BasicEncoder.valueEncode(value!!)
    }
}
