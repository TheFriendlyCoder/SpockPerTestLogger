package ca.thefriendlycoder.spockpertestlogger

import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Entrypoint class for the Spock extension
 *
 * For details on how this class works, see the Spock extension docs here:
 *      https://spockframework.org/spock/docs/2.3/extensions.html#_global_extensions
 */
class SpockPerTestLoggerExtension implements IGlobalExtension {
    ConfigFile cfg
    LogManager logManager

    /**
     * Callback that runs as soon as the Spock framework launches
     */
    @Override
    void start() {
        // Make sure our log folder exists and any historic data is purged
        def dir = Paths.get(cfg.logPath)
        if (Files.exists(dir)) {
            Files.walk(dir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete)
        }

        Files.createDirectories(dir)

        // Initialize or log manager instance, so its ready for capturing log
        // output later
        this.logManager = new LogManager(Paths.get(cfg.logPath), "%msg%n")
    }

    /**
     * Callback that runs for each spec in the test suite
     *
     * This callback gets run for every spec, whether it contains features / tests that are
     * being executed by the current run or not (ie: even tests that excluded from selection
     * will be passed through this callback)
     *
     * @param spec metadata describing the current spec being processed
     */
    @Override
    void visitSpec(SpecInfo spec) {
        for (def f : spec.features) {
            if (f.dataVariables.size() > 0) {
                // If the feature is a data-drive test we need to register
                // our processor with a slightly different interceptor so
                // that it is invoked once for each iteration of the test
                f.addIterationInterceptor(new LogProcessor(logManager))
            } else {
                f.addInterceptor(new LogProcessor(logManager))
            }
        }
    }
}