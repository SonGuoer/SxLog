package com.nandi.sxlog.handler

import com.alibaba.fastjson.JSON
import com.nandi.sxlog.LOG
import com.nandi.sxlog.LoggerPrinter
import com.nandi.sxlog.utils.Parser
import com.nandi.sxlog.utils.Utils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
Date:2018/8/13
Time:下午4:38
author:qingsong
 */

class CollectionHandler:BaseHandler(), Parser<Collection<*>> {

    override fun handle(obj: Any): Boolean {

        if (obj is Collection<*>) {

            val value = obj.firstOrNull()
            val isPrimitiveType = Utils.isPrimitiveType(value)

            if (isPrimitiveType) {
                val simpleName = obj.javaClass
                var msg = "%s size = %d" + LoggerPrinter.BR
                msg = String.format(msg, simpleName, obj.size) + "║ "
                val s = LOG.getMethodNames()
                println(String.format(s, msg + obj.toString()))
                return true
            }

            val s = LOG.getMethodNames()
            println(String.format(s, parseString(obj)))

            return true
        }

        return false
    }

    override fun parseString(collection: Collection<*>): String {

        val jsonArray = JSONArray()

        val simpleName = collection.javaClass

        var msg = "%s size = %d" + LoggerPrinter.BR
        msg = String.format(msg, simpleName, collection.size) + "║ "

        collection.map {

            it ->

            try {
                val objStr = JSON.toJSONString(it)
                val jsonObject = JSONObject(objStr)
                jsonArray.put(jsonObject)
            } catch (e: JSONException) {
                LOG.e("Invalid Json")
            }
        }

        var message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        return msg + message
    }

}