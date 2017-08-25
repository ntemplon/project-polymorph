package com.polymorph.game.io

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.config.Configuration
import org.apache.logging.log4j.core.config.ConfigurationFactory
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder
import org.apache.logging.log4j.core.config.ConfigurationSource
import java.net.URI
import sun.security.x509.OIDMap.addAttribute
import org.apache.logging.log4j.core.appender.ConsoleAppender
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder
import com.kotcrab.vis.ui.layout.DragPane.DragPaneListener.ACCEPT
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.Filter
import org.apache.logging.log4j.core.config.Order
import org.apache.logging.log4j.core.config.plugins.Plugin


/**
 * Copyright (c) 2017 Nathan S. Templon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
object Log {

    init {
        ConfigurationFactory.setConfigurationFactory(LogConfigurationFactory)
    }

    private val logger = LogManager.getLogger()

    fun warn(message: () -> Any) {
        if (logger.isWarnEnabled) {
            logger.warn(message.invoke())
        }
    }

    fun warn(message: Any) {
        if (logger.isWarnEnabled) {
            logger.warn(message)
        }
    }

    fun info(message: () -> Any) {
        if (logger.isInfoEnabled) {
            logger.info(message.invoke())
        }
    }

    fun info(message: Any) {
        if (logger.isInfoEnabled) {
            logger.info(message)
        }
    }

    fun debug(message: () -> Any) {
        if (logger.isDebugEnabled) {
            logger.debug(message.invoke())
        }
    }

    fun debug(message: Any) {
        if (logger.isDebugEnabled) {
            logger.debug(message)
        }
    }

    object LogConfigurationFactory : ConfigurationFactory() {
        private fun createConfiguration(name: String, builder: ConfigurationBuilder<BuiltConfiguration>): Configuration {
            builder.setConfigurationName(name)
            builder.setStatusLevel(Level.ERROR)
            builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL).addAttribute("level", Level.DEBUG))

            val appenderBuilder = builder.newAppender("Stdout", "CONSOLE").addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT)
            appenderBuilder.add(builder.newLayout("PatternLayout").addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"))
            appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
                    Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"))

            val fileAppenderBuilder = builder.newAppender("fileout", "File").addAttribute("fileName", FileLocations.rootDirectory.resolve("ProjectPolymorph.log").toString()).addAttribute("append", false)
            fileAppenderBuilder.add(builder.newLayout("PatternLayout").addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"))
            fileAppenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY, Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"))

            builder.add(appenderBuilder)
            builder.add(fileAppenderBuilder)
            builder.add(builder.newLogger("com.polymorph.game", Level.DEBUG).add(builder.newAppenderRef("fileout")).addAttribute("additivity", false))
            builder.add(builder.newRootLogger(Level.ERROR).add(builder.newAppenderRef("fileout")))
            return builder.build()
        }

        override fun getConfiguration(loggerContext: LoggerContext, source: ConfigurationSource): Configuration {
            return getConfiguration(loggerContext, source.toString(), null)
        }

        override fun getConfiguration(loggerContext: LoggerContext, name: String, configLocation: URI?): Configuration {
            val builder = newConfigurationBuilder()
            return createConfiguration(name, builder)
        }

        override fun getSupportedTypes(): Array<String> {
            return arrayOf("*")
        }
    }
}