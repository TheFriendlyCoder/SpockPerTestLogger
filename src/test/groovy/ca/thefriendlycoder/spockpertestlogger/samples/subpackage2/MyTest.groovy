package ca.thefriendlycoder.spockpertestlogger.samples.subpackage2

import org.slf4j.LoggerFactory
import spock.lang.Specification

/**
 * This spec matches the same name and features as those in
 * subpackage1.
 *
 * Used for ensuring there are no name clashes between tests
 * with the same name in different packages
 */
class MyTest extends Specification {
    static String expectedMessage = "Inside fake unit test 2"
    void "test method"() {
        expect:
        LoggerFactory.getLogger(MyTest.class).info(expectedMessage)
    }
}