package ca.thefriendlycoder.spockpertestlogger

import ch.qos.logback.classic.Logger
import spock.lang.Specification
import org.slf4j.LoggerFactory

class SampleTest extends Specification {
    // TODO: add this test suite to ignore list (or find a way to ignore nested test suites
    //       so they are only run by the embedded runner)
    void "test method"() {
        expect:
        println("Inside fake unit test")
        def a = LoggerFactory.getLogger(SampleTest.class)
        println("Logger ${a.name} is of type ${a.class}")
        def b = (Logger)a
        def app = b.getAppender("test-appender")
        println("Appender is ${app}")
        a.error("KSP was here in logging")
    }
}
