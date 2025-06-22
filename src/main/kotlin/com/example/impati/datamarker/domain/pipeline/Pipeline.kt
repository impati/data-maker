package com.example.impati.datamarker.domain.pipeline

import java.util.*

/**
 * 회원 가입 후 회원 토큰을 받아서 저장하고 있다가
 * 영화를 검색 한 뒤
 * 와치리스트에 추가한다.
 *
 * N(1 호출 -> 1호출)
 * N(1 호출 -> N호출)
 */
class Pipeline(
    val steps: List<Step>,
    val name: String,
    val id: String = UUID.randomUUID().toString().substring(0, 7)
) {
}
