package com.nandi.sxlog.handler

import com.nandi.sxlog.LOG
import com.nandi.sxlog.LoggerPrinter
import com.nandi.sxlog.utils.Parser
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
Date:2018/8/13
Time:下午4:36
author:qingsong
 */

class StringHandler:BaseHandler(), Parser<String> {

    override fun handle(obj: Any): Boolean {

        if (obj is String) {

            var json = obj.trim { it <= ' ' }
            val s = LOG.getMethodNames()
            println(String.format(s, parseString(json)))
            return true
        }

        return false
    }

    override fun parseString(json: String): String {
        var message: String = ""

        try {
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
            } else if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
                message = message.replace("\n".toRegex(), "\n║ ")
            }
        } catch (e: JSONException) {
            LOG.e("Invalid Json: " + json)
            message = ""
        }

        return message
    }

}