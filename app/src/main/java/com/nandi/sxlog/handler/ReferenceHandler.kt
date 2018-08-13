package com.nandi.sxlog.handler

import com.alibaba.fastjson.JSON
import com.nandi.sxlog.LOG
import com.nandi.sxlog.LoggerPrinter
import com.nandi.sxlog.utils.Parser
import com.nandi.sxlog.utils.Utils
import org.json.JSONObject
import java.lang.ref.Reference

/**
Date:2018/8/13
Time:下午4:51
author:qingsong
 */

class ReferenceHandler:BaseHandler(), Parser<Reference<*>> {

    override fun handle(obj: Any): Boolean {

        if (obj is Reference<*>) {
            val s = LOG.getMethodNames()
            println(String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(reference: Reference<*>): String {
        val actual = reference.get()

        var msg = reference.javaClass.canonicalName + "<" + actual?.javaClass?.simpleName + ">"+ LoggerPrinter.BR + "║ "

        val isPrimitiveType = Utils.isPrimitiveType(actual)

        if (isPrimitiveType) {

            msg += "{" + actual + "}"
        } else {

            val jsonObject = JSONObject(JSON.toJSONString(actual))
            var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
            message = message.replace("\n".toRegex(), "\n║ ")
            msg += message
        }

        return msg
    }
}