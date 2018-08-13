package com.nandi.sxlog.utils


interface Parser<T> {

    fun parseString(t: T): String
}