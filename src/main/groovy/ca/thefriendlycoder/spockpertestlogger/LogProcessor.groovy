package ca.thefriendlycoder.spockpertestlogger

import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Log manager which is executed for each feature selected for the current test run
 *
 * <p>For more details, see here:
 *      https://spockframework.org/spock/docs/2.3/extensions.html#_interceptors
 */
class LogProcessor implements IMethodInterceptor {
    private final ConfigFile cfg

    /**
     * Constructor
     *
     * @param cfg reference to the application config parsed from the Spock config file
     */
    LogProcessor(ConfigFile cfg) {
        this.cfg = cfg
    }

    /**
     * Callback run when a feature (aka: unit test) is about to be executed
     *
     * @param invocation metadata about the current feature about to be executed
     * @throws Throwable on error
     */
    @Override
    void intercept(IMethodInvocation invocation) throws Throwable {
        // Construct a fake log file
        var outputFile = Paths.get(cfg.logPath, invocation.feature.spec.name, invocation.feature.name + ".log").toFile()
        Files.createDirectories(outputFile.parentFile.toPath())
        outputFile.createNewFile()
        println("KSP: " + outputFile)

        // Run the test
        invocation.proceed()
    }
}