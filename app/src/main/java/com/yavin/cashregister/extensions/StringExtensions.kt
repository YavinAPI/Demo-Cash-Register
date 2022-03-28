package com.yavin.macewindu.utils.extensions

fun String.addCharAtIndex(char: Char, index: Int) =
        StringBuilder(this).apply {
            insert(index, char)
        }.toString()