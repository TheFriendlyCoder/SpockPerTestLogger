package ca.thefriendlycoder.spockpertestlogger

import org.slf4j.LoggerFactory
import spock.lang.Specification

class SampleTest extends Specification {
    void "test method"() {
        expect:
        LoggerFactory.getLogger(SampleTest.class).info("Inside fake unit test")
    }
}