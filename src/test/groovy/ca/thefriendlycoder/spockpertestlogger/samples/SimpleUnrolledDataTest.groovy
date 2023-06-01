package ca.thefriendlycoder.spockpertestlogger.samples

import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

/**
 * This sample specification has a data driven test, which should result in
 * multiple log files being generated, one for each iteration of the test.
 *
 * This test differs from SimpleDataTest in that it uses the Unroll annotation
 * to unroll the feature iteration so there's a separate test for each
 */
class SimpleUnrolledDataTest extends Specification {
    static String firstMessage = "First test iteration"
    static String secondMessage = "Second test iteration"

    @Unroll
    def "Data Test"() {
        expect:
        LoggerFactory.getLogger(SimpleUnrolledDataTest.class).info(message)
        a + b > 0

        where:
        a | b | message
        1 | 2 | firstMessage
        2 | 3 | secondMessage
    }
}
