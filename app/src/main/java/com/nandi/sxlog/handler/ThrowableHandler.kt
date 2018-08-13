package com.nandi.sxlog.handler

import com.nandi.sxlog.LOG
import com.nandi.sxlog.utils.Parser
import java.io.PrintWriter
import java.io.StringWriter

/**
Date:2018/8/13
Time:下午4:49
author:qingsong
 */

class ThrowableHandler:BaseHandler(), Parser<Throwable> {

    override fun handle(obj: Any): Boolean {

        if (obj is Throwable) {

            val s = LOG.getMethodNames()
            System.err.println(String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(throwable: Throwable): String {
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        throwable.printStackTrace(pw)
        pw.flush()
        var message = sw.toString()
        message = message.replace("\n".toRegex(), "\n║ ")

        return message
    }

}