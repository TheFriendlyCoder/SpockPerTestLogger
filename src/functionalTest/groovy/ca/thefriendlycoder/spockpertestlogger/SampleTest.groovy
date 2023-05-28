package ca.thefriendlycoder.spockpertestlogger

import spock.lang.Specification
import org.slf4j.LoggerFactory

class SampleTest extends Specification {
    // TODO: add this test suite to ignore list (or find a way to ignore nested test suites
    //       so they are only run by the embedded runner)
    void "test method"() {
        expect:
        println("Inside fake unit test")
        LoggerFactory.getLogger(SampleTest.class).error("KSP was here in logging")
    }
}
