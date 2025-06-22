package com.example.impati.datamarker.domain

data class QueryParam(
    val key: String,
    val value: String,
    val variable: Variable? = null
) {

    init {
        val regex = Regex("""\{([^}]+)}""")
        if (regex.matches(value)) {
            requireNotNull(variable)
        }
    }
}
