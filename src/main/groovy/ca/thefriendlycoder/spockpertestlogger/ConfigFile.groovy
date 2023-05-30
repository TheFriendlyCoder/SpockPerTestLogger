package ca.thefriendlycoder.spockpertestlogger

import spock.config.ConfigurationObject

import java.nio.file.Paths

@ConfigurationObject("PerTestLogger")
class ConfigFile {
    public String logPath = Paths.get("build", "logs").toAbsolutePath().toString()
}