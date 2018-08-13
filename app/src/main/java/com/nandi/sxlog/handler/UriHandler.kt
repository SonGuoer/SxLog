package com.nandi.sxlog.handler

import android.net.Uri
import com.nandi.sxlog.LOG
import com.nandi.sxlog.LoggerPrinter
import com.nandi.sxlog.utils.Parser
import org.json.JSONObject

/**
Date:2018/8/13
Time:下午4:49
author:qingsong
 */

class UriHandler:BaseHandler(), Parser<Uri> {

    override fun handle(obj: Any): Boolean {

        if (obj is Uri) {

            val s = LOG.getMethodNames()
            println(String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(uri: Uri): String {

        var msg = uri.javaClass.toString() + LoggerPrinter.BR + "║ "

        val jsonObject = JSONObject()
        jsonObject.put("Scheme", uri.scheme)
        jsonObject.put("Host", uri.host)
        jsonObject.put("Port", uri.port)
        jsonObject.put("Path", uri.path)
        jsonObject.put("Query", uri.query)
        jsonObject.put("Fragment", uri.fragment)

        var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        return msg + message
    }

}