package ca.thefriendlycoder.spockpertestlogger

import java.nio.file.Paths
import spock.config.ConfigurationObject

@ConfigurationObject("PerTestLogger")
/**
 * Parsing class used to extract configuration information for our extension from the Spock config file
 *
 * <p>See here for more details:
 *      https://spockframework.org/spock/docs/2.3/extensions.html#_configuration_objects
 *
 * <p>The config section will be marked with the name provided to the annotation above.
 *
 * <pre>
 * PerTestLogger {
 *     logPath "/path/to/log/folder"
 * }
 * </pre>
 */
class ConfigFile {
    public String logPath = Paths.get("build", "logs").toAbsolutePath().toString()
}