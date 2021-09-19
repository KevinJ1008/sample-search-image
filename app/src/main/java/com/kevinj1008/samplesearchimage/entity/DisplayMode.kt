package com.kevinj1008.samplesearchimage.entity

enum class DisplayMode(val value: Int) {
    LIST(1),
    //We are not sure how many columns we need to support, so we just limit value to prevent remote
    // control issue
    GRID(2),
    UNKNOWN(-999);

    companion object {
        fun getDisplayMode(value: Int): DisplayMode {
            return values().find {
                it.value == value
            } ?: UNKNOWN
        }
    }
}