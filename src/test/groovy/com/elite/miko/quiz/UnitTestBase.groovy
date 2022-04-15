package com.elite.miko.quiz

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import spock.lang.Specification

import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime

class UnitTestBase extends Specification {

    static def jsonBasePath = "src/test/resources/json/"

    /**
     * テスト用に固定の時間を設定したClockを作成する
     * @return 2021:12:12 T10:15:00が設定されたClock
     */
    static Clock mockClock() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(
                2021, 12, 12,
                10, 15, 0, 999999999, ZoneId.of("Asia/Tokyo")
        )
        Clock clock = Clock.fixed(zonedDateTime.toInstant(), ZoneId.of("Asia/Tokyo"))
        return clock
    }

    /**
     * Jsonファイルからjavaオブジェクトへの変換を行う
     *
     * @param jsonDir jsonの配置されているディレクトリへのパス
     * @param fileName 変換に使用するjsonのファイル名
     * @param typeReference 変換後のjavaの型を指定した jackson TypeReference
     * @return 変換後のjavaオブジェクト
     */
    static def readJsonToObject(String jsonDir, String fileName, TypeReference typeReference) {
        def jsonPath = jsonBasePath + jsonDir + fileName + ".json"
        def objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JavaTimeModule())
        return objectMapper.readValue(new File(jsonPath), typeReference)
    }
}
