package com.punch.navdisplay

data class NavInfo(
    val line1: String, // Arrow + Distance + Action
    val line2: String  // Road name + ETA
)

object NavParser {

    fun parse(title: String, text: String, useAscii: Boolean): NavInfo {
        val lowerTitle = title.lowercase()
        val arrow = getArrow(lowerTitle, useAscii)

        // Clean up common repetitive words to save precious 3.5" screen space
        var cleanTitle = title
            .replace("Turn right", "Turn R", ignoreCase = true)
            .replace("Turn left", "Turn L", ignoreCase = true)
            .replace("Keep right", "Keep R", ignoreCase = true)
            .replace("Keep left", "Keep L", ignoreCase = true)
            .replace("Continue straight", "Straight", ignoreCase = true)
            .replace("Head ", "", ignoreCase = true)
            .trim()

        val line1 = "$arrow $cleanTitle".take(26)
        val line2 = text.take(26)

        return NavInfo(line1 = line1, line2 = line2)
    }

    private fun getArrow(text: String, useAscii: Boolean): String {
        return when {
            text.contains("u-turn") || text.contains("make a u-turn") -> {
                if (useAscii) "U-Turn" else "⮌"
            }
            text.contains("sharp right") -> {
                if (useAscii) "|->" else "↱"
            }
            text.contains("sharp left") -> {
                if (useAscii) "<-|" else "↰"
            }
            text.contains("slight right") || text.contains("keep right") -> {
                if (useAscii) "-->" else "↗"
            }
            text.contains("slight left") || text.contains("keep left") -> {
                if (useAscii) "<--" else "↖"
            }
            text.contains("right") -> {
                if (useAscii) "-->" else "➜"
            }
            text.contains("left") -> {
                if (useAscii) "<--" else "⮌"
            }
            text.contains("roundabout") || text.contains("rotary") || text.contains("circle") -> {
                if (useAscii) "(O)" else "⟳"
            }
            text.contains("straight") || text.contains("continue") || text.contains("stay on") -> {
                if (useAscii) "^^" else "↑"
            }
            else -> {
                if (useAscii) ">>" else "•"
            }
        }
    }
}
