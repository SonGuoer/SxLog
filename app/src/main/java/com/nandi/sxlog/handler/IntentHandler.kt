package com.nandi.sxlog.handler

import android.content.Intent
import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.nandi.sxlog.LOG
import com.nandi.sxlog.LoggerPrinter
import com.nandi.sxlog.utils.Parser
import com.nandi.sxlog.utils.Utils
import org.json.JSONException
import org.json.JSONObject

/**
Date:2018/8/13
Time:下午4:47
author:qingsong
 */

class IntentHandler:BaseHandler(), Parser<Intent> {

    override fun handle(obj: Any): Boolean {

        if (obj is Intent) {

            val s = LOG.getMethodNames()
            println(String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(intent: Intent): String {

        var msg = intent.javaClass.toString() + LoggerPrinter.BR + "║ "

        val jsonObject = JSONObject()
        jsonObject.put("Scheme", intent.scheme)
        jsonObject.put("Action", intent.action)
        jsonObject.put("DataString", intent.dataString)
        jsonObject.put("Type", intent.type)
        jsonObject.put("Package", intent.`package`)
        jsonObject.put("ComponentInfo", intent.component)
//        jsonObject.put("Flags", getFlags(intent.flags))
        jsonObject.put("Categories", intent.categories)
        if (intent.extras!=null) {
            jsonObject.put("Extras", JSONObject(parseBundleString(intent.extras)))
        }

        var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        return msg + message
    }

    private fun parseBundleString(extras: Bundle): String {

        val jsonObject = JSONObject()
        for (key in extras.keySet()) {

            val isPrimitiveType = Utils.isPrimitiveType(extras.get(key))

            try {

                if (isPrimitiveType) {
                    jsonObject.put(key.toString(), extras.get(key))
                } else {
                    jsonObject.put(key.toString(), JSONObject(JSON.toJSONString(extras.get(key))))
                }
            } catch (e: JSONException) {
                LOG.e("Invalid Json")
            }

        }

        return jsonObject.toString(LoggerPrinter.JSON_INDENT)
    }
}