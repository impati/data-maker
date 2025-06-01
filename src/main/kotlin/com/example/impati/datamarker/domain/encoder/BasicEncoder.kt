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
                // List 내부 아이템을 valueEncode로 다시 재귀 호출
                val encodedElements = value.map { element ->
                    // 리스트가 Any? 타입(List<Any?>)일 수 있으니 null 체크
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

    fun propertiesEncode(properties: List<Property>): String {
        val builder = StringBuilder()
        builder.append("{")
        builder.append(properties.joinToString(",") { it.build() })
        builder.append("}")

        return builder.toString()
    }
}
