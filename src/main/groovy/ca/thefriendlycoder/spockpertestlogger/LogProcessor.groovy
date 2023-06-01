package ca.thefriendlycoder.spockpertestlogger

import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

/**
 * Log manager which is executed for each feature selected for the current test run
 *
 * <p>For more details, see here:
 *      https://spockframework.org/spock/docs/2.3/extensions.html#_interceptors
 */
class LogProcessor implements IMethodInterceptor {
    private final LogManager logManager
    /**
     * Constructor
     *
     * @param logManager object which controls the capturing of log output for the test suite
     */
    LogProcessor(LogManager logManager) {
        this.logManager = logManager
    }

    /**
     * Callback run when a feature (aka: unit test) is about to be executed
     *
     * @param invocation metadata about the current feature about to be executed
     * @throws Throwable on error
     */
    @Override
    void intercept(IMethodInvocation invocation) throws Throwable {
        logManager.configureLogger(invocation.feature.parent.pkg, invocation.feature.spec.name, invocation.feature.name)
        try {
            // Run the test
            invocation.proceed()
        } finally {
            // V1 release:
            // TODO: See what happens if we have 2 tests with same name in different sub-packages
            // TODO: Test with data driven test - both with rolled and unrolled test names
            // TODO: test publishing logic
            // TODO: add support for jar signing - needed for Maven publish
            // TODO: add spotless support with validation
            // TODO: generate API docs
            // TODO: generate user docs (mkdocs)
            // Enhancements
            // TODO: Detect if test passed and delete log file if it did
            //          https://stackoverflow.com/questions/50666160/find-the-outcome-status-of-a-test-in-specification-cleanup
            // TODO: add support for specSetup/specCleanup operations (probably need separate spec-specific log files)
            // TODO: add feature to customize log message format to config file
            // TODO: Unit test running tests in parallel
            //      https://stackoverflow.com/questions/8355847/how-to-log-multiple-threads-in-different-log-files
            // TODO: Test with Groovy4 to see if it works with Spock Groovy 3 - may need different builds / releases
            logManager.reset()
        }
    }
}