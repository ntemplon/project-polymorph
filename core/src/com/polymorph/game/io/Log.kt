package com.polymorph.game.io

import com.badlogic.gdx.Gdx
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
import com.polymorph.game.PolymorphGame
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

    fun fatal(message: () -> Any) {
        if (logger.isFatalEnabled) {
            logger.fatal(message.invoke())
        }
    }

    fun fatal(message: Any) {
        if (logger.isFatalEnabled) {
            logger.fatal(message)
        }
    }

    fun fatal(throwable: Throwable) {
        if (logger.isFatalEnabled) {
            logger.fatal(throwable)
        }
    }

    fun error(message: () -> Any) {
        if (logger.isErrorEnabled) {
            logger.error(message.invoke())
        }
    }

    fun error(message: Any) {
        if (logger.isErrorEnabled) {
            logger.error(message)
        }
    }

    fun error(throwable: Throwable) {
        if (logger.isErrorEnabled) {
            logger.error(throwable)
        }
    }

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

    fun warn(throwable: Throwable) {
        if (logger.isWarnEnabled) {
            logger.warn(throwable)
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

    fun info(throwable: Throwable) {
        if (logger.isInfoEnabled) {
            logger.info(throwable)
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

    fun debug(throwable: Throwable) {
        if (logger.isDebugEnabled) {
            logger.debug(throwable)
        }
    }

    fun trace(message: () -> Any) {
        if (logger.isTraceEnabled) {
            logger.trace(message.invoke())
        }
    }

    fun trace(message: Any) {
        if (logger.isTraceEnabled) {
            logger.trace(message)
        }
    }

    fun trace(throwable: Throwable) {
        if (logger.isTraceEnabled) {
            logger.trace(throwable)
        }
    }

    object GdxLogger: com.badlogic.gdx.ApplicationLogger {
        override fun error(tag: String?, message: String?) {
            Log.error { "${tag ?: ""}:${message ?: ""}" }
        }

        override fun error(tag: String?, message: String?, exception: Throwable?) {
            Log.error { "${tag ?: ""}:${message ?: ""}" }
            if (exception != null) {
                Log.error(exception)
            }
        }

        override fun log(tag: String?, message: String?) {
            Log.info { "${tag ?: ""}:${message ?: ""}" }
        }

        override fun log(tag: String?, message: String?, exception: Throwable?) {
            Log.info { "${tag ?: ""}:${message ?: ""}" }
            if (exception != null) {
                Log.info(exception)
            }
        }

        override fun debug(tag: String?, message: String?) {
            Log.debug{ "${tag ?: ""}:${message ?: ""}" }
        }

        override fun debug(tag: String?, message: String?, exception: Throwable?) {
            Log.debug { "${tag ?: ""}:${message ?: ""}" }
            if (exception != null) {
                Log.debug(exception)
            }
        }
    }

    object LogConfigurationFactory : ConfigurationFactory() {
        private fun createConfiguration(name: String, builder: ConfigurationBuilder<BuiltConfiguration>): Configuration {
            val logLevel = if(PolymorphGame.arguments.useCustomLogLevel) PolymorphGame.arguments.logLevel else Level.ALL

            builder.setConfigurationName(name)
            builder.setStatusLevel(Level.ERROR)
            builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL).addAttribute("level", logLevel))

            val fileAppenderBuilder = builder.newAppender("fileout", "File").addAttribute("fileName", FileLocations.rootDirectory.resolve("ProjectPolymorph.log").toString()).addAttribute("append", false)
            fileAppenderBuilder.add(builder.newLayout("PatternLayout").addAttribute("pattern", "[%d{dd MMM yyyy HH:mm:ss}] %-5level: %msg%n%throwable"))
            fileAppenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY, Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"))

            builder.add(fileAppenderBuilder)
            builder.add(builder.newLogger("com.polymorph.game", logLevel).add(builder.newAppenderRef("fileout")).addAttribute("additivity", false))
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