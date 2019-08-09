@file:Suppress("unused")

package cn.wj.android.base.tools

import cn.wj.android.base.constants.DATE_FORMAT_DEFAULT
import cn.wj.android.base.log.Logger
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/* ----------------------------------------------------------------------------------------- */
/* |                                        时间相关                                        | */
/* ----------------------------------------------------------------------------------------- */

/**
 * 格式化日期、时间
 *
 * @param format 日期时间格式
 */
fun <N : Number> N.dateFormat(format: String = DATE_FORMAT_DEFAULT): String {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).format(this)
    } catch (e: ParseException) {
        Logger.t("Time").e("dateFormat", e)
        ""
    }
}

/**
 * 格式化日期、时间
 *
 * @param format 日期时间格式
 */
fun Date.dateFormat(format: String = DATE_FORMAT_DEFAULT): String {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).format(this)
    } catch (e: ParseException) {
        Logger.t("Time").e("dateFormat", e)
        ""
    }
}

/**
 * 将时间字符串格式化为指定类型
 */
fun String.paresDate(format: String): Date {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).parse(this)
    } catch (e: ParseException) {
        Logger.t("Time").e("paresDate", e)
        Date()
    }
}

/**
 * 将字符串时间转换为 Long 类型时间
 */
fun String?.toLongTime(format: String = DATE_FORMAT_DEFAULT): Long {
    return if (this.isNullOrEmpty()) {
        Date().time
    } else {
        try {
            SimpleDateFormat(format, Locale.getDefault()).parse(this).time
        } catch (e: ParseException) {
            Logger.t("Time").e("toLongTime", e)
            Date().time
        }
    }
}