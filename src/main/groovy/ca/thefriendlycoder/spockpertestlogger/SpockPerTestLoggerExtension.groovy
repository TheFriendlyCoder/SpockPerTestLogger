package ca.thefriendlycoder.spockpertestlogger

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.FileAppender
import org.slf4j.LoggerFactory
import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.SpecInfo

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.logging.Logger

class SpockPerTestLoggerExtension implements IGlobalExtension {
    SpockConfig cfg
    LoggerContext lctx

    @Override
    void start() {
        // Clear LogBack config
        println("Starting plugin...")
        println("KSP log path is " + cfg.logPath)
        return
        var tempCtx = LoggerFactory.getILoggerFactory()
        if (tempCtx instanceof LoggerContext) {
            lctx = (LoggerContext)tempCtx
        } else {
            // TODO: Add support for other logging frameworks like log4j
            println("Not supported logger type:" + tempCtx.class)
            lctx = new LoggerContext()
            //return
        }
        // TODO: Consider having a flag in config file that disables this reset logic
        //       ie: to allow users to configure logging in the environment instead
        //lctx.reset()

        // Clear Java Logger JUL config
//        Logger l = Logger.getLogger("")
//        for (def h: l.handlers) {
//            l.removeHandler(h)
//        }

        def dir = Paths.get(cfg.logPath)
        if (Files.exists(dir)) {
            Files.walk(dir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete)
        }
        Files.createDirectories(dir)
        if (!Files.isDirectory(dir)) {
            // TODO: see what exception type to throw here
            throw new Exception("Log path " + cfg.logPath + " is not a folder")
        }
        // TODO: See how best to handle notification messages here
        //       maybe a good spot for Gradle lifetime logger
        println("Using log folder " + cfg.logPath)

    }

    @Override
    void visitSpec(SpecInfo spec) {
//        for (def c : spec.features) {
//            c.addInterceptor(new Processor())
//        }
    }


    class Processor implements IMethodInterceptor {
        @Override
        void intercept(IMethodInvocation invocation) throws Throwable {

//            if (lctx == null) {
//                invocation.proceed()
//                return
//            }

//            var tempCtx = LoggerFactory.getILoggerFactory()
//            def temp = tempCtx.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME)
//            //OutputEventListenerBackedLoggerContext b = (OutputEventListenerBackedLoggerContext)tempCtx
//            //def temp = LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME)
//            if (!temp instanceof ch.qos.logback.classic.Logger) {
//                throw new Exception("Unsupported logger type " + temp.class.toString())
//            }
//            println(temp.class)
//            def root2 = (ch.qos.logback.classic.Logger)temp


            var outputFile = Paths.get(cfg.logPath, invocation.feature.spec.name, invocation.feature.name + ".txt").toFile()
            // Setup log format
            def msgPattern = cfg.logPattern
            PatternLayoutEncoder encoder = new PatternLayoutEncoder()
            encoder.setPattern(msgPattern)
            //encoder.setContext(tempCtx)
            //encoder.setContext(root.getLoggerContext())
            encoder.setContext(lctx)
            encoder.start()

            // Configure file appender
            def fa = new FileAppender()
            fa.setAppend(true)
            fa.setEncoder(encoder)
            fa.setContext(lctx)
            //fa.setContext(root.getLoggerContext())
            //fa.setContext(tempCtx)
            fa.clearAllFilters()
            fa.setFile(outputFile.toString())
            fa.setName("test-appender")
            fa.start()

            // Configure the root logger
            //def root = (ch.qos.logback.classic.Logger)temp
            //def root = new ch.qos.logback.classic.Logger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME, null, lctx)
            def root = lctx.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME)
            root.addAppender(fa)
            root.setLevel(Level.ALL)

            try {
                // Run the test
                invocation.proceed()
            } finally {
                // Clean up
                root.detachAppender(fa)
                // TODO: delete log file if test succeeded
            }
        }
    }
}
