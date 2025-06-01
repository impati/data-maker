package com.example.impati.datamarker.domain.generator

import com.example.impati.datamarker.domain.DataType
import com.example.impati.datamarker.domain.Domain
import com.example.impati.datamarker.domain.DomainType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.regex.Pattern

class StringValueGeneratorTest {

    private var sut: ValueGenerator = StringValueGenerator()

    @Test
    fun `도메인 설정이 없는 경우 랜덤 문자열을 생성한다`() {
        val domain: Domain? = null

        val result = sut.generate(DataType.STRING, domain)

        assertThat(result).isInstanceOf(String::class.java)
    }

    @Test
    fun `multiple true 도메인 설정인 경우 여러 랜덤 문자열을 생성한다`() {
        val domain = Domain(multiple = true)

        val result = sut.generate(DataType.STRING, domain)

        assertThat(result).isInstanceOf(List::class.java)
        val list = result as List<*>
        assertThat(list).isNotEmpty().allMatch { it is String }
        assertThat(list.size).isBetween(1, 5)
    }

    @Test
    fun `domainType 이 ENUM 인 경우 values 값의 문자열을 생성한다`() {
        val possibleValues = listOf("RED", "GREEN", "BLUE")
        val domain = Domain(type = DomainType.ENUM, multiple = false, values = possibleValues)

        val result = sut.generate(DataType.STRING, domain) as String

        // 반환된 문자열이 values 목록 중 하나여야 한다
        assertThat(possibleValues).contains(result)
    }

    @Test
    fun `domainType 이 ENUM 이고 multiple true 인 경우 values 값의 문자열을 여러개 생성한다`() {
        val possibleValues = listOf("SMALL", "MEDIUM", "LARGE")
        val domain = Domain(type = DomainType.ENUM, multiple = true, values = possibleValues)

        val result = sut.generate(DataType.STRING, domain)

        // 리스트 타입이고, 각 요소가 possibleValues 중 하나여야 한다
        assertThat(result).isInstanceOf(List::class.java)
        val list = result as List<*>
        assertThat(list).isNotEmpty()
            .allMatch { it is String && possibleValues.contains(it) }
    }

    @Test
    fun `domainType 이 EMAIL 인 경우 email 형태의 문자열을 생성한다`() {
        val domain = Domain(type = DomainType.EMAIL, multiple = false)

        val result = sut.generate(DataType.STRING, domain) as String

        // 이메일 패턴 검증: 로컬 파트 5자 + '@' + 도메인(예: example.com)
        val emailPattern = Pattern.compile("^[A-Za-z0-9]{5}@(example\\.com|test\\.com|mail\\.com)$")
        assertThat(emailPattern.matcher(result).matches())
            .isTrue()
    }

    @Test
    fun `domainType 이 IMAGE 인 경우 image 주소 문자열을 생성한다`() {
        val domain = Domain(type = DomainType.IMAGE, multiple = false)

        val result = sut.generate(DataType.STRING, domain) as String

        // URL 패턴 검증: https://picsum.photos/seed/{UUID}/200/200 형태
        val imagePattern = Pattern.compile("^https://picsum\\.photos/seed/[0-9a-fA-F\\-]{36}/200/200$")
        assertThat(imagePattern.matcher(result).matches())
            .isTrue()
    }

    @Test
    fun `domainType 이 RANGE 인 경우 from ~ to 길이를 가진 랜덤 문자열을 생성한다`() {
        val minLen = 3
        val maxLen = 7
        val domain = Domain(type = DomainType.RANGE, multiple = false, from = minLen, to = maxLen)

        val result = sut.generate(DataType.STRING, domain) as String

        // 길이가 minLen 이상, maxLen 이하인지 확인
        assertThat(result.length).isBetween(minLen, maxLen)
        // 알파벳+숫자만 포함되어 있는지도 확인
        assertThat(result).matches("^[A-Za-z0-9]{$minLen,${maxLen}}$")
    }
}
