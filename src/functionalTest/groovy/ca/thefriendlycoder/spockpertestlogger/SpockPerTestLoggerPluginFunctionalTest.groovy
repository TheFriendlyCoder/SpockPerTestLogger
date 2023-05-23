/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package ca.thefriendlycoder.spockpertestlogger

import spock.lang.Specification
import spock.lang.TempDir
import org.gradle.testkit.runner.GradleRunner

/**
 * A simple functional test for the 'spockpertestlogger.greeting' plugin.
 */
class SpockPerTestLoggerPluginFunctionalTest extends Specification {
    @TempDir
    private File projectDir

    private getBuildFile() {
        new File(projectDir, "build.gradle")
    }

    private getSettingsFile() {
        new File(projectDir, "settings.gradle")
    }

    def "can run task"() {
        given:
        settingsFile << ""
        buildFile << """
plugins {
    id('ca.thefriendlycoder.spockpertestlogger')
}
"""

        when:
        def runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("ptlShowErrors")
        runner.withProjectDir(projectDir)
        def result = runner.build()

        then:
        result.output.contains("Showing test errors")
    }
}
