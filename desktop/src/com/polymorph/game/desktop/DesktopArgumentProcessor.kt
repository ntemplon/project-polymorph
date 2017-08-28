package com.polymorph.game.desktop

import com.polymorph.game.ProgramArguments
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.apache.logging.log4j.Level

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
object DesktopArgumentProcessor {
    private val debugOption: Option = Option("debug", "output debug information in the log")
    private val rootFolderOption: Option = Option("root", true, "specify a filepath for the root directory relative to the working folder")
    private val logLevelOption: Option = Option("logLevel", true, "specify the level of message to log in this instance")
    private val options: Options = options {
        addOption(debugOption)
        addOption(rootFolderOption)
        addOption(logLevelOption)
    }

    fun process(args: Array<String>): ProgramArguments {
        val parser = DefaultParser()
        val cl = parser.parse(options, args)

        val debug = cl.hasOption(debugOption.opt)
        val useRoot = cl.hasOption(rootFolderOption.opt)
        val rootDir = if(useRoot) cl.getOptionValue(rootFolderOption.opt) else ProgramArguments.ROOT_DIRECTORY_DEFAULT
        val useLogLevel: Boolean = cl.hasOption(logLevelOption.opt)
        val logLevel: Level = if(useLogLevel) Level.valueOf(cl.getOptionValue(logLevelOption.opt)) else ProgramArguments.LOG_LEVEL_DEFAULT

        return ProgramArguments(
                debug = debug,
                useCustomRootDirectory = useRoot,
                rootDirectory = rootDir,
                useCustomLogLevel = useLogLevel,
                logLevel = logLevel)
    }
}

fun options(op: Options.() -> Unit): Options = Options().apply(op)
fun optionsOf(vararg options: Option): Options {
    val collection = Options()
    for(opt in options) {
        collection.addOption(opt)
    }
    return collection
}