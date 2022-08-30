package com.example.firstcomposeactivity.misc.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.json.JSONObject


inline fun <reified T> Moshi.objectToJson(data: T): String? =
    adapter<T>(T::class.java).toJson(data)

inline fun <reified T> Moshi.jsonToObject(data: String?): T? =
    data?.let { adapter<T>(T::class.java).fromJson(data) }


inline fun <reified T> Moshi.objectToJsonObject(data: T): Any? =
    adapter<T>(Types.newParameterizedType(JSONObject::class.java, T::class.java))
        .toJsonValue(data)

inline fun <reified T> Moshi.jsonObjectToObject(data: JSONObject?): T? =
    adapter<T>(Types.newParameterizedType(JSONObject::class.java, T::class.java))
        .fromJsonValue(data)


inline fun <reified T> Moshi.multipleListToJson(list: List<List<T>>): String? =
    adapter<List<List<T>>>(Types.newParameterizedType(List::class.java, T::class.java))
        .toJson(list)

inline fun <reified T> Moshi.jsonToMultipleList(json: String): List<List<T>>? =
    adapter<List<List<T>>>(Types.newParameterizedType(List::class.java, T::class.java))
        .fromJson(json)

