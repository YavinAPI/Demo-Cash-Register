package com.yavin.macewindu.utils.extensions

fun <K, V> Map<out K, V>.getOrDefaultCompat(key: K, defaultValue: V): V {
    if (this.containsKey(key)) {
        return this[key] ?: defaultValue
    }
    return defaultValue
}


fun <K, V> Map<out K, V>.getIntOrDefault(key: K, defaultValue: Int): Int {
    try {
        if (this.containsKey(key)) {
            return this[key].toString().toIntOrNull() ?: defaultValue
        }
    } catch (exception: Exception) {
        exception.printStackTrace()
    }

    return defaultValue
}

fun <K, V> Map<out K, V>.getStringOrDefault(key: K, defaultValue: String): String {
    try {
        if (this.containsKey(key)) {
            return this[key].toString()
        }
    } catch (exception: Exception) {
        exception.printStackTrace()
    }

    return defaultValue
}