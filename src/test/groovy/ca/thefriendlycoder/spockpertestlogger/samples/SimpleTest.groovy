package ca.thefriendlycoder.spockpertestlogger.samples

import org.slf4j.LoggerFactory
import spock.lang.Specification

/**
 * Test that outputs a simple log message during a test feature
 *
 * Used for testing basic mechanics of our log capture extension
 *
 * This test is not intended to be called directly, but rather
 * should be called indirectly from a Spock EmbeddedSpecRunner.
 */
class SimpleTest extends Specification {
    static String expectedMessage = "Inside fake unit test"
    void "test method"() {
        expect:
        LoggerFactory.getLogger(SimpleTest.class).info(expectedMessage)
    }
}