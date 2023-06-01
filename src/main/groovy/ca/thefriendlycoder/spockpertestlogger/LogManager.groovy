package ca.thefriendlycoder.spockpertestlogger

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.FileAppender
import org.slf4j.LoggerFactory

import java.nio.file.Path
import java.nio.file.Paths

/**
 * This class manages the manipulation of different Java logging subsystems
 * supported by the extension
 */
class LogManager {
    private LoggerContext lctx
    private final Path logPath
    private final String messagePattern
    private Logger rootLogger
    private PatternLayoutEncoder encoder
    private FileAppender lastAppender

    /**
     * Constructor
     *
     * @param logPath location where log files should be stored
     * @param messagePattern Message pattern describing the format of log messages being captured
     */
    LogManager(Path logPath, String messagePattern) {
        this.logPath = logPath
        this.messagePattern = messagePattern
        var tempCtx = LoggerFactory.getILoggerFactory()
        if (tempCtx instanceof LoggerContext) {
            lctx = (LoggerContext)tempCtx
        } else {
            // TODO: write unit test for this case
            //       probably need to add a new test suite with no logback dependencies in it
            lctx = new LoggerContext()
        }
        this.rootLogger = lctx.getLogger(Logger.ROOT_LOGGER_NAME)
        this.encoder = new PatternLayoutEncoder()
        encoder.setPattern(messagePattern)
        encoder.setContext(lctx)
        encoder.start()
    }

    /**
     * Begins capturing log output for a test feature
     *
     * This method injects a custom logger into the root namespace and sets it
     * to capture ALL log output from all sources, ensuring the maximum verbosity
     * of log output is captured
     *
     * A new log file, named after the currently running test, will be generated
     * in the log folder provided in the config
     *
     * @param packageName namespace of the package containing the test spec
     * @param specName the name of the Spock specification currently running
     * @param featureName the name of the Spock feature currently running
     */
    void configureLogger(String packageName, String specName, String featureName) {
        var outputFile = logPath.resolve(Paths.get("${packageName}.${specName}", featureName + ".log")).toFile()
        println("Output file " + outputFile.toString())
        // Configure file appender
        lastAppender = new FileAppender()
        lastAppender.setAppend(true)
        lastAppender.setEncoder(encoder)
        lastAppender.setContext(lctx)
        lastAppender.clearAllFilters()
        lastAppender.setFile(outputFile.toString())
        lastAppender.setName("test-appender")
        lastAppender.start()

        // Configure the root logger
        rootLogger.addAppender(lastAppender)
        rootLogger.setLevel(Level.ALL)
    }

    /**
     * Removes the captured log output in preparation for the next test fixture
     */
    void reset() {
        rootLogger.detachAppender(lastAppender)
    }
}
