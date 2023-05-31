package ca.thefriendlycoder.spockpertestlogger.samples

import org.slf4j.LoggerFactory
import spock.lang.Specification

/**
 * This sample test spec logs messages from the feature setup / cleanup methods so
 * we can make sure that output is properly captured
 */
class FeatureSetupTeardown extends Specification{
    static String setupMessage = "Logging from setup"
    static String teardownMessage = "Logging from cleanup"

    def setup() {
        LoggerFactory.getLogger(FeatureSetupTeardown.class).info(setupMessage)
    }

    def cleanup() {
        LoggerFactory.getLogger(FeatureSetupTeardown.class).info(teardownMessage)
    }

    def "Test Feature"() {
        expect:
        true
    }

}
