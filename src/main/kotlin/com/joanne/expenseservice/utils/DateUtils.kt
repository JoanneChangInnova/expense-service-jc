package com.joanne.expenseservice.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val ISO_DATE_TIME_PATTERN: String = "yyyy-MM-d'T'HH:mm:ss.SSS'Z'"
    private const val ISO_DATE_PATTERN: String = "yyyy-MM-d"

    /**
     * @param: yyyy-MM-d'T'HH:mm:ss.SSS'Z'
     * @return: Date()
     */
    fun dateStrToIsoDateTime(dateStr: String?): Date{
        return convertStrToDate(dateStr, ISO_DATE_TIME_PATTERN)
    }

    /**
     * @param: yyyy-MM-dd
     * @return: Date()
     */
    fun dateStrToIsoDate(dateStr: String?): Date{
        return convertStrToDate(dateStr, ISO_DATE_PATTERN)
    }

    private fun convertStrToDate(dateStr: String?, pattern: String?): Date{
        return SimpleDateFormat(pattern).parse(dateStr)
    }

}