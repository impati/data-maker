package com.example.impati.datamarker.domain

import java.time.LocalDateTime

data class Domain(
    val values: List<String> = mutableListOf(),
    val from: Int? = null,
    val to: Int? = null,
    val start: LocalDateTime? = null,
    val end: LocalDateTime? = null,
    val type: DomainType? = null,
    val multiple: Boolean = false
) {


}
