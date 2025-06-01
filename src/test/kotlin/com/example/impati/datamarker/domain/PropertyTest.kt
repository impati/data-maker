package com.example.impati.datamarker.domain

import com.example.impati.datamarker.domain.encoder.BasicEncoder
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PropertyTest {


    @Test
    fun test() {
        val property = Property(
            type = DataType.STRING,
            name = "notice"
        )

        val result = property.build()

        /**
         *  "notice" : "hello"
         */
        println("result = $result")
    }

    @Test
    fun test2() {
        val property = Property(
            type = DataType.OBJECT,
            name = "detail",
            properties = listOf(
                Property(
                    type = DataType.STRING,
                    name = "notice"
                ),
                Property(
                    type = DataType.LONG,
                    name = "size"
                )
            )
        )

        val result = property.build()

        /**
         *   "detail" : {
         *      "notice" : "hello",
         *      "size" : 15
         *   }
         */
        println("result = $result")
    }

    @Test
    fun test3() {
        val properties = listOf(
            Property(
                type = DataType.OBJECT,
                name = "detail",
                properties = listOf(
                    Property(
                        type = DataType.STRING,
                        name = "notice"
                    ),
                    Property(
                        type = DataType.LONG,
                        name = "size"
                    )
                )
            ),
            Property(
                type = DataType.STRING,
                name = "name",
            )
        )

        val result = BasicEncoder.propertiesEncode(properties)

        /**
         * {
         *   "detail" : {
         *      "notice" : "hello",
         *      "size" : 15
         *   },
         *   "name": "hello"
         * }
         */
        println("result = $result")
    }

    @Test
    fun test5() {
        val properties = listOf(
            Property(
                type = DataType.STRING,
                name = "tags",
                domain = Domain(
                    multiple = true
                )
            )
        )

        val result = BasicEncoder.propertiesEncode(properties)

        /**
         * {"tags":["hello","world"]}
         *
         */
        println("result = $result")
    }

    @Test
    fun test6() {
        val properties = listOf(
            Property(
                type = DataType.OBJECT,
                name = "detail",
                properties = listOf(
                    Property(
                        type = DataType.STRING,
                        name = "notice",
                    ),
                    Property(
                        type = DataType.LONG,
                        name = "size",
                        domain = Domain(
                            type = DomainType.RANGE,
                            from = 1,
                            to = 20
                        )
                    )
                )
            ),

            Property(
                type = DataType.LONG,
                name = "price",
            ),

            Property(
                type = DataType.STRING,
                name = "name",
            ),

            Property(
                type = DataType.STRING,
                name = "category",
                domain = Domain(
                    type = DomainType.ENUM,
                    values = listOf("BOOKS", "FOOD")
                )
            ),

            Property(
                type = DataType.DATE,
                name = "release",
                domain = Domain(
                    type = DomainType.RANGE,
                    start = LocalDateTime.of(2024, 1, 1, 0, 0),
                    end = LocalDateTime.of(2025, 1, 1, 0, 0),
                )
            ),


            Property(
                type = DataType.STRING,
                name = "image",
                domain = Domain(
                    type = DomainType.IMAGE
                )
            ),


            Property(
                type = DataType.STRING,
                name = "email",
                domain = Domain(
                    type = DomainType.EMAIL
                )
            ),

            Property(
                type = DataType.STRING,
                name = "tag",
                domain = Domain(
                    multiple = true
                )
            ),
        )

        val result = BasicEncoder.propertiesEncode(properties)

        println("result = $result")
    }
}
