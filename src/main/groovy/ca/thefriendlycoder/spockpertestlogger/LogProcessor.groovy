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
        logManager.configureLogger(invocation.feature.spec.name, invocation.feature.name)
        try {
            // Run the test
            invocation.proceed()
        } finally {
            logManager.reset()
        }
    }
}