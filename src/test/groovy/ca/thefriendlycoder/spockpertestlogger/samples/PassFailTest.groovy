package ca.thefriendlycoder.spockpertestlogger.samples

import org.slf4j.LoggerFactory
import spock.lang.Specification

/**
 * This sample test specification contains one test that passes all
 * expectations and another that fails its expectations. This allows
 * us to verify the behavior of our Spock extension works as expected
 * in both cases.
 */
public class PassFailTest extends Specification {
    static String passMessage = "Unit test passed"
    static String failMessage = "Unit test failed"

    def "Passing Test"() {
        expect:
        LoggerFactory.getLogger(PassFailTest.class).info(passMessage)
    }

    def "Failing Test"() {
        expect:
        LoggerFactory.getLogger(PassFailTest.class).info(failMessage)
        false
    }
}
