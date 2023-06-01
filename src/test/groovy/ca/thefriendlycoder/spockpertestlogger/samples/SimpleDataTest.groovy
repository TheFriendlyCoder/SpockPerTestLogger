package ca.thefriendlycoder.spockpertestlogger.samples

import org.slf4j.LoggerFactory
import spock.lang.Specification

/**
 * This sample specification has a data driven test, which should result in
 * multiple log files being generated, one for each iteration of the test.
 */
class SimpleDataTest extends Specification {
    static String firstMessage = "First test iteration"
    static String secondMessage = "Second test iteration"

    def "Data Test"() {
        expect:
        LoggerFactory.getLogger(SimpleDataTest.class).info(message)
        a + b < 0

        where:
        a | b | message
        1 | 2 | firstMessage
        2 | 3 | secondMessage
    }
}
