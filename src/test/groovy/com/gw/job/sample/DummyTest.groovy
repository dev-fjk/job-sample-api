package com.gw.job.sample

import spock.lang.Specification

class DummyTest extends Specification {

    Dummy target

    def setup() {
        target = new Dummy()
    }

    def "動作検証"() {
        when:
        def actual = target.helloWorld()
        then:
        actual == "Hello World"
    }
}
