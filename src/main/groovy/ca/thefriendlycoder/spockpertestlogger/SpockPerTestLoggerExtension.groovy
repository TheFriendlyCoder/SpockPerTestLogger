package ca.thefriendlycoder.spockpertestlogger

import org.spockframework.runtime.extension.IGlobalExtension

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Entrypoint class for the Spock extension
 */
class SpockPerTestLoggerExtension implements IGlobalExtension {
    ConfigFile cfg

    @Override
    void start() {
        def dir = Paths.get(cfg.logPath)
        if (!Files.exists(dir)) {
            Files.createDirectories(dir)
        }
    }

}