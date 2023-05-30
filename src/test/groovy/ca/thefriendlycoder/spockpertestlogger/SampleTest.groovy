package ca.thefriendlycoder.spockpertestlogger

import spock.lang.Specification

class SampleTest extends Specification {
    void "test method"() {
        expect:
        println("Inside fake unit test")
    }
}