package ca.thefriendlycoder.spockpertestlogger.samples

import org.slf4j.LoggerFactory
import spock.lang.Specification

/**
 * This sample test spec logs messages from the specification setup / cleanup methods so
 * we can make sure that output is properly captured
 */
class SpecSetupTeardown extends Specification{
    static String setupMessage = "Logging from setup"
    static String teardownMessage = "Logging from cleanup"

    def setupSpec() {
        LoggerFactory.getLogger(SpecSetupTeardown.class).info(setupMessage)
    }

    def cleanupSpec() {
        LoggerFactory.getLogger(SpecSetupTeardown.class).info(teardownMessage)
    }

    def "Test Feature"() {
        expect:
        true
    }
}
