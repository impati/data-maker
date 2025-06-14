package com.example.impati.datamarker.domain.encoder

import com.example.impati.datamarker.domain.Property
import java.time.LocalDateTime

object BasicEncoder {

    fun valueEncode(value: Any): String {
        return when (value) {
            is String -> "\"$value\""
            is LocalDateTime -> "\"$value\""
            is Int -> value.toString()
            is List<*> -> {
                val encodedElements = value.map { element ->
                    if (element == null) {
                        "null"
                    } else {
                        valueEncode(element)
                    }
                }
                "[${encodedElements.joinToString(separator = ",")}]"
            }

            else -> value.toString()
        }
    }

    fun keyEncode(str: String): String {
        return "\"$str\":"
    }

    fun objectEncode(strings: List<String>): String {
        val builder = StringBuilder()
        builder.append("{")
        builder.append(strings.joinToString(","))
        builder.append("}")

        return builder.toString()
    }

    fun encodePayload(properties: List<Property>): String {
        val builder = StringBuilder()
        builder.append("{")
        builder.append(properties.joinToString(",") { it.build() })
        builder.append("}")

        return builder.toString()
    }
}
