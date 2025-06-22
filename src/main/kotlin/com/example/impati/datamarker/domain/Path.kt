package com.example.impati.datamarker.domain

data class Path(
    val value: String,
    val pathVariable: List<Variable>
) {

    init {
        val regex = Regex("""\{([^}]+)}""")
        val names = regex.findAll(value)
            .map { it.groupValues[1] }
            .toList()

        require(names.size == pathVariable.size)
        require(names.containsAll(pathVariable.stream().map { it.name }.toList()))
    }

    companion object {
        fun none(): Path {
            return Path("", listOf())
        }
    }

    fun isPathVariableEmpty(): Boolean {
        return pathVariable.isEmpty()
    }
}
