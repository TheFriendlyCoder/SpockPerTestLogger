package ca.thefriendlycoder.spockpertestlogger

import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.model.SpecInfo

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Entrypoint class for the Spock extension
 */
class SpockPerTestLoggerExtension implements IGlobalExtension {
    ConfigFile cfg

    /**
     * Callback that runs as soon as the Spock framework launches
     */
    @Override
    void start() {
        def dir = Paths.get(cfg.logPath)
        if (!Files.exists(dir)) {
            Files.createDirectories(dir)
        }
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
        for (def c : spec.features) {
            c.addInterceptor(new LogProcessor(cfg))
        }
    }

}