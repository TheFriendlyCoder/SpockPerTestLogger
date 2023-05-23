package ca.thefriendlycoder.spockpertestlogger

import groovy.transform.ToString
import spock.config.ConfigurationObject

import java.nio.file.Paths

@ConfigurationObject("PerTestLogger")
@ToString
class SpockConfig {
    public String logPath = Paths.get("logs").toAbsolutePath().toString()
    public String logPattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger - %msg%n"
}