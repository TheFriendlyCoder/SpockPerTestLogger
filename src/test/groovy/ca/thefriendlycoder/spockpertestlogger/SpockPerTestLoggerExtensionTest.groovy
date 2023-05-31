/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package ca.thefriendlycoder.spockpertestlogger

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.TempDir
import spock.util.EmbeddedSpecRunner

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Subject(SpockPerTestLoggerExtension)
class SpockPerTestLoggerExtensionTest extends Specification {
    @TempDir
    Path tempdir

    def "Test log folder gets created"() {
        given: "A temp folder that doesn't already exist"
        def expPath = tempdir.resolve("fubar")

        and: "A sample Spock test spec"
        def specRunner = new EmbeddedSpecRunner()
        specRunner.throwFailure = false
        specRunner.configurationScript {
            PerTestLogger {
                logPath expPath.toString()
            }
        }

        when: "We try running the sample spec"
        def results = specRunner.runClass(SampleTest)

        then: "The test suite should succeed without error"
        results.testEvents()
            .debug()
            .assertStatistics(stats -> stats.failed(0))

        and: "The log folder should have been created by the plugin"
        expPath.toFile().exists()
    }

    def "Test log folder already exists"() {
        given: "A temp folder that already exists"
        def expPath = tempdir.resolve("fubar")
        Files.createDirectories(expPath)

        and: "A sample Spock test spec"
        def specRunner = new EmbeddedSpecRunner()
        specRunner.throwFailure = false
        specRunner.configurationScript {
            PerTestLogger {
                logPath expPath.toString()
            }
        }

        when: "We try running the sample spec"
        def results = specRunner.runClass(SampleTest)

        then: "The test suite should succeed without error"
        results.testEvents()
            .debug()
            .assertStatistics(stats -> stats.failed(0))
    }

    def "Test log file gets created"() {
        given: "A temp folder that doesn't already exist"
        def expPath = tempdir.resolve(Paths.get("SampleTest", "test method.log"))

        and: "A sample Spock test spec"
        def specRunner = new EmbeddedSpecRunner()
        specRunner.throwFailure = false
        specRunner.configurationScript {
            PerTestLogger {
                logPath tempdir.toString()
            }
        }

        when: "We try running the sample spec"
        def results = specRunner.runClass(SampleTest)

        then: "The test suite should succeed without error"
        results.testEvents()
            .debug()
            .assertStatistics(stats -> stats.failed(0))

        and: "The log file should have been created by the plugin"
        expPath.toFile().exists()
    }
}
