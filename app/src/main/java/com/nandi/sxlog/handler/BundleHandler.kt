package com.nandi.sxlog.handler

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
Time:下午4:27
author:qingsong
*/


class BundleHandler:BaseHandler(), Parser<Bundle> {

    override fun handle(obj: Any): Boolean {

        if (obj is Bundle) {

            val s = LOG.getMethodNames()
            println(String.format(s, parseString(obj)))
            return true
        }

        return false
    }

    override fun parseString(bundle: Bundle): String {

        var msg = bundle.javaClass.toString() + LoggerPrinter.BR + "║ "

        val jsonObject = JSONObject()
        for (key in bundle.keySet()) {

            val isPrimitiveType = Utils.isPrimitiveType(bundle.get(key))

            try {

                if (isPrimitiveType) {
                    jsonObject.put(key.toString(), bundle.get(key))
                } else {
                    jsonObject.put(key.toString(), JSONObject(JSON.toJSONString(bundle.get(key))))
                }
            } catch (e: JSONException) {
                LOG.e("Invalid Json")
            }

        }

        var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        return msg + message
    }
}