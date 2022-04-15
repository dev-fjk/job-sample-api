package com.gw.job.sample.resource

import spock.lang.Specification

class HelloWorldControllerSpec extends Specification {

    HelloWorldController target

    def setup() {
        target = new HelloWorldController()
    }

    def "サンプル"() {
        when:
        def actual = target.helloWorld()
        then:
        actual == "Hello World!"
    }
}
