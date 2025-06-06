package com.example.impati.datamarker.domain.job

data class Tps(
    val value: Long
) {
    init {
        require(value in 1..100) { "TPS 는 0보다 크고 100보다 작거나 같아야합니다." }
    }
}
