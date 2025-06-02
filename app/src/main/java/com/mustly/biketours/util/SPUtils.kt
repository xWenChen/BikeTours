package com.mustly.biketours.util

import android.content.Context
import android.content.SharedPreferences
import com.mustly.biketours.MainApplication

object SPUtils {
    private const val defaultName = "settings"

    fun sp(spName: String = defaultName): SharedPreferences {
        return MainApplication.context.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    /**
     * 写入数据，仅支持基本类型，需要异步调用
     * */
    suspend fun SharedPreferences.put(key: String, value: Any?) = with(edit()) {
        value ?: return false
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            is String? -> putString(key, value)
            else -> return false
        }.commit() // 同步提交（需返回值时使用）
        //.apply() // 异步提交（推荐）
    }

    /**
     * 读取数据，需要异步调用
     * */
    suspend fun SharedPreferences.getInt(key: String?, defaultValue: Int = 0): Int {
        key ?: return defaultValue
        return getInt(key, defaultValue)
    }

    /**
     * 读取数据，需要异步调用
     * */
    suspend fun SharedPreferences.getLong(key: String?, defaultValue: Long = 0L): Long {
        key ?: return defaultValue
        return getLong(key, defaultValue)
    }

    /**
     * 读取数据，需要异步调用
     * */
    suspend fun SharedPreferences.getFloat(key: String?, defaultValue: Float = 0f): Float {
        key ?: return defaultValue
        return getFloat(key, defaultValue)
    }

    /**
     * 读取数据，需要异步调用
     * */
    suspend fun SharedPreferences.getBoolean(key: String?, defaultValue: Boolean = false): Boolean {
        key ?: return defaultValue
        return getBoolean(key, defaultValue)
    }

    /**
     * 读取数据，需要异步调用
     * */
    suspend fun SharedPreferences.getString(key: String?, defaultValue: String? = null): String? {
        key ?: return defaultValue
        return getString(key, defaultValue)
    }

    /**
     * 删除数据，需要异步调用
     * */
    suspend fun SharedPreferences.remove(key: String) = edit().remove(key).commit()
    /**
     * 删除数据，需要异步调用
     * */
    suspend fun SharedPreferences.clear() = edit().clear().commit()
}
