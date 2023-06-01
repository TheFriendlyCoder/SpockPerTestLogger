package ca.thefriendlycoder.spockpertestlogger.samples

import org.slf4j.LoggerFactory
import spock.lang.Specification

/**
 * This sample specification has several simple, small test features that can
 * be used for testing batch test execution. In particular, it may be used
 * for testing our extension using parallel execution of tests
 */
public class MultiFeatureTest extends Specification {
    static String expectedMessage1 = "Inside first test"
    static String expectedMessage2 = "Inside second test"
    static String expectedMessage3 = "Inside third test"
    static String expectedMessage4 = "Inside fourth test"
    static String expectedMessage5 = "Inside fifth test"

    def "First test"() {
        expect:
        LoggerFactory.getLogger(MultiFeatureTest.class).info(expectedMessage1)
    }
    def "Second test"() {
        expect:
        LoggerFactory.getLogger(MultiFeatureTest.class).info(expectedMessage2)
    }
    def "Third test"() {
        expect:
        LoggerFactory.getLogger(MultiFeatureTest.class).info(expectedMessage3)
    }
    def "Fourth test"() {
        expect:
        LoggerFactory.getLogger(MultiFeatureTest.class).info(expectedMessage4)
    }
    def "Fifth test"() {
        expect:
        LoggerFactory.getLogger(MultiFeatureTest.class).info(expectedMessage5)
    }

}
