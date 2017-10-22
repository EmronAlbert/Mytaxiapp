package com.mytaxiapp.util

import android.content.Context

/**
 * @author Tosin Onikute.
 */

class StringUtil(private val mContext: Context) {
    companion object {

        fun isBlank(string: CharSequence?): Boolean {
            return string == null || string.toString().trim { it <= ' ' }.length == 0
        }

        fun toTitleCase(str: String?): String? {
            if (str == null) {
                return null
            }

            var space = true
            val builder = StringBuilder(str)
            val len = builder.length

            for (i in 0..len - 1) {
                val c = builder[i]
                if (space) {
                    if (!Character.isWhitespace(c)) {
                        // Convert to title case and switch out of whitespace mode.
                        builder.setCharAt(i, Character.toTitleCase(c))
                        space = false
                    }
                } else if (Character.isWhitespace(c)) {
                    space = true
                } else {
                    builder.setCharAt(i, Character.toLowerCase(c))
                }
            }

            return builder.toString()
        }
    }
}
