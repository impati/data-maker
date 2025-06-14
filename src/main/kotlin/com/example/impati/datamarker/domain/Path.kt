package com.example.impati.datamarker.domain

data class Path(
    val value: String,
    val pathVariable: List<PathVariable>
) {

    init {
        val regex = Regex("""\{([^}]+)\}""")
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

    fun build(): String {
        var result = value
        for (variable in pathVariable) {
            val name = variable.name
            result = result.replace("{$name}", variable.build())
        }
        
        return result
    }
}
