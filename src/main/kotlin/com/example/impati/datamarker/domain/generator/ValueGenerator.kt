package com.example.impati.datamarker.domain.generator

import com.example.impati.datamarker.domain.DataType
import com.example.impati.datamarker.domain.Domain

interface ValueGenerator {


    fun generate(type: DataType, domain: Domain?): Any
}
