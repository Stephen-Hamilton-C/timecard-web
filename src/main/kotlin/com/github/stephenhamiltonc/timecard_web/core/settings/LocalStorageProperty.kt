package com.github.stephenhamiltonc.timecard_web.core.settings

import kotlinx.browser.localStorage
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.reflect.KProperty

class LocalStorageProperty<T: Any>(
    private val _key: String,
    private val _defaultValue: T,
    private val _serializer: KSerializer<T>
) {
    private lateinit var _value: T

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if(!::_value.isInitialized) {
            val data = localStorage.getItem(_key)
            _value = if(data == null) {
                _defaultValue
            } else {
                Json.decodeFromString(_serializer, data)
            }
        }

        return _value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if(_value == value) return
        _value = value
        val data = Json.encodeToString(_serializer, value)
        localStorage.setItem(_key, data)
    }
}