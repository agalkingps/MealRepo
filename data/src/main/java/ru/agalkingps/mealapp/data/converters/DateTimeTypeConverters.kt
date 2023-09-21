package ru.agalkingps.mealapp.data.converters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

/**
 * Room [TypeConverter] functions for various `java.time.*` classes.
 */
object DateTimeTypeConverters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date): Long{
        return date.time;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let { OffsetDateTime.parse(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(value) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun toDuration(value: Long?): Duration? {
        return value?.let { Duration.ofMillis(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun fromDuration(value: Duration?): Long? {
        return value?.toMillis()
    }
}
