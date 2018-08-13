package com.nandi.sxlog.handler

import com.alibaba.fastjson.JSON
import com.nandi.sxlog.LOG
import com.nandi.sxlog.LoggerPrinter
import org.json.JSONObject

/**
Date:2018/8/13
Time:下午4:51
author:qingsong
 */

class ObjectHandler:BaseHandler() {

    override fun handle(obj: Any): Boolean {

        val s = LOG.getMethodNames()

        var msg = obj.javaClass.toString() + LoggerPrinter.BR + "║ "
        val objStr = JSON.toJSONString(obj)
        val jsonObject = JSONObject(objStr)
        var message = jsonObject.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        println(String.format(s, msg + message))

        return true
    }
}