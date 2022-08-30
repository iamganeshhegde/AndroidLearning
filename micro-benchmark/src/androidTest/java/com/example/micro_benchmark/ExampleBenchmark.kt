package com.example.micro_benchmark

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.benchmark.Utils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.TypeAdapter
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import dev.zacsweers.jsonserialization.models.moshiKotlinCodegen.KCGResponse
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Type

/**
 * Benchmark, which will execute on an Android device.
 *
 * The body of [BenchmarkRule.measureRepeated] is measured in a loop, and Studio will
 * output the result. Modify your code to see how it affects performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()


    val readFileWithoutNewLineFromResources =
        Utils.readFileWithoutNewLineFromResources("largesample.json")

    var json =
//            "[{\"type\":\"parent\",\"text\":\"parent\",\"name\":\"name\",\"occupation\":\"ocup\"},{\"type\":\"child\",\"gift\":\"parent\",\"name\":\"childName\",\"occupation\":\"ocup\"}]"
        "{\"type\":\"parent\",\"gift\":\"parent\",\"name\":\"name\",\"occupation\":\"ocup\",\"salary\":\"sal\"}"

    val gson = GsonBuilder().create()
    var cusAdapter = UserClassAdapter()
    val moshi = Moshi.Builder().add(cusAdapter).build()

    val jsonAdapter: JsonAdapter<User> = moshi.adapter<User>(User::class.java)

    val deserializer = object : JsonDeserializer<User> {
        val gsonBuilder = GsonBuilder()
        val customGson: Gson = gsonBuilder.create()


        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): User? {
            val jsonObject: JsonObject? = json?.asJsonObject

            if (jsonObject?.has("text") == true) {
                return customGson.fromJson(jsonObject, User.Parent::class.java)
            } else if (jsonObject?.has("giftId") == true) {
                return customGson.fromJson(jsonObject, User.Child::class.java)
            }

            return User()
        }
    }

    private val customGson: Gson = GsonBuilder()
        .registerTypeAdapter(User::class.java, deserializer)
        .setLenient().create()

    //            moshi.jsonToObject<User>(json)
    //            val newMoshi = Moshi.Builder().build()
//            jsonAdapter.fr

    /*@Test
    fun log() {
        benchmarkRule.measureRepeated {
            val answer = jsonAdapter.fromJson(json)
//
//            moshi.jsonToObject<User.Child>(json)
        }


    }*/

/*
    @Test
    fun logGson() {
        benchmarkRule.measureRepeated {

//            gson.fromJson(json, User.Child::class.java)
            val answer = customGson.fromJson(json,User::class.java)
        }


    }
*/





    @Test
    fun gson_reflective_string_fromJson() = benchmarkRule.measureRepeated {
        val param = runWithTimingDisabled {
            ReflectiveGson(readFileWithoutNewLineFromResources) }
        val pojo = param.adapter.fromJson(readFileWithoutNewLineFromResources)
    }

    @Test
    fun logTestMoshi() = benchmarkRule.measureRepeated {
        val param = runWithTimingDisabled {
            CodegenMoshiKotlin(readFileWithoutNewLineFromResources) }
        val pojo = param.adapter.fromJson(readFileWithoutNewLineFromResources)
    }

    inner class UserClassAdapter {

        @FromJson
//    fun fromJson(json: String): GenericExamples.User {
        fun fromJson(reader: JsonReader): User? {

            reader.isLenient = true
            return (reader.readJsonValue() as? Map<String, Any>)?.let { data ->
                try {
                    val jsonObject = JSONObject(data)

//                val moshi = Moshi.Builder().build()
                    if (jsonObject.has("text")) {

//                    var jsonToObjectParent =
//                        return moshi.jsonToObject<User.Parent>(jsonObject.toString())
//                    println("json to user class object parent- $jsonToObjectParent")

//                    return@let jsonToObjectParent
                    } else if (jsonObject.has("gift")) {
//                    var jsonToObject =

                        val moshii = Moshi.Builder().build()
                        val jsonAdapter: JsonAdapter<User> = moshii.adapter<User>(User::class.java)

                        return jsonAdapter.fromJson(jsonObject.toString())
//                        return moshi.jsonToObject<User.Child>(jsonObject.toString())

//                    println("json to user class object child- $jsonToObject")

//                    return@let jsonToObject
                    }
                    User()
                } catch (e: JSONException) {
//                 Handle exception
                    null
                }
            }
        }

        @ToJson
        fun toJson(writer: JsonWriter, value: User?) {
//        var moshi = Moshi.Builder().build()
//        moshi.objectToJsonObject<GenericExamples.User>(writer)

            value?.let {
                writer.value(Buffer().writeUtf8(value.toString()))
            }
        }
    }
}


class CodegenMoshiKotlin(json: String) {

    private val moshi: Moshi = Moshi.Builder()
        .build()
    val response: KCGResponse
    val adapter: JsonAdapter<KCGResponse>

    init {
        adapter = moshi.adapter(KCGResponse::class.java)
        response = adapter.fromJson(json)!!
    }
}


@JsonClass(generateAdapter = true)
open class User() {
    @JsonClass(generateAdapter = true)
    data class Parent(
        var text: String,
        var type: String,
        var name: String,
        var occupation: String,
        var salary: String
    ) : User()


    @JsonClass(generateAdapter = true)
    data class Child(
        var gift: String,
        var type: String,
        var name: String,
        var occupation: String,
        var salary: String
    ) : User()

    override fun toString(): String {
        return super.toString()
    }
}


class ReflectiveGson(json: String) {

    private val gson: Gson = GsonBuilder().create()
    val response: KCGResponse
    val adapter: TypeAdapter<KCGResponse>

    init {
        adapter = gson.getAdapter(KCGResponse::class.java)
        response = adapter.fromJson(json)
    }
}


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

