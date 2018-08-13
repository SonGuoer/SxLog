package com.nandi.sxlog.utils


object Utils {

    /**
     * 判断是否基本类型
     */
    @JvmStatic
    fun isPrimitiveType(value: Any?): Boolean = when (value) {

        is Boolean -> true

        is String -> true

        is Int -> true

        is Float -> true

        is Double -> true

        else -> false
    }

}
