package com.demo.compose.utils

import androidx.compose.ui.unit.sp


fun Int.toStringWithWhitespace(): String {
    val base = toString()
    val sb = StringBuilder()
    sb.append(base)

    val indices = when(base.length) {
        0 -> arrayOf()
        1 -> arrayOf()
        2 -> arrayOf()
        3 -> arrayOf()
        4 -> arrayOf(1)
        5 -> arrayOf(2)
        6 -> arrayOf(3)
        7 -> arrayOf(1, 5)
        8 -> arrayOf(2, 6)
        9 -> arrayOf(3, 7)
        10 -> arrayOf(1, 5, 9)
        11 -> arrayOf(2, 6, 10)
        else -> throw IllegalArgumentException("Out of bounds")
    }
    for (i in indices) {
        sb.insert(i, ' ')
    }

//    val spaceCount = (base.length - 1) / 3
//    val spacePositions = IntArray(spaceCount) { 0 }
//    for (i in spacePositions.indices) {
//        spacePositions[i] = base.length - ((i + 1) * 3)
//    }
//    println("Indices1: ${spacePositions.joinToString(", ")}")
//    for (i in spacePositions.indices) {
//        spacePositions[spacePositions.lastIndex - i] += i
//    }
//    println("Indices2: ${spacePositions.joinToString(", ")}")
//    for (i in spacePositions.reversed()) {
//        sb.insert(i, ' ')
//    }


    return sb.toString()
}