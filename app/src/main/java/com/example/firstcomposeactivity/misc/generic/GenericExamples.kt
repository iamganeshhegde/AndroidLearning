package com.example.firstcomposeactivity.misc.generic

import android.os.Parcelable
import com.google.gson.Gson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import kotlinx.parcelize.Parcelize
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import java.util.*


class GenericExamples {


    class Rank<T>(var rank: T) {

        fun printRank(name: String) {
            println("THe Rank of $name is $rank")
        }

    }

    fun main() {
        val rank = Rank<Int>(10)
        rank.printRank("Ganesh")

        val rank2 = Rank<String>("first")
        rank2.printRank("Ganesh")


        getStringValue()


    }

    @JsonClass(generateAdapter = true)
    data class SingleListItem(
        val title: String,
        val number: Int
    )

    @JsonClass(generateAdapter = true)
    data class NameClass(
        val name: String,
        val id: Int
    )

    fun getStringValue() {

        var list = mutableListOf<SingleListItem>(
            SingleListItem("title1", 1),
            SingleListItem("title2", 2),
            SingleListItem("title3", 3)
        )

        var listMyData =
            Types.newParameterizedType(MutableList::class.java, SingleListItem::class.java)
        val adapter: JsonAdapter<List<SingleListItem>> = Moshi.Builder().build().adapter(listMyData)
//        val moshi = Moshi.Builder().build()
//        val jsonAdapter: JsonAdapter<AdaptiveBitRatioAnalytics> = moshi.adapter(AdaptiveBitRatioAnalytics::class.java)

        println("adapter - to json")
        println(adapter.toJson(list.toList()))


        println("list - to string")
        println(list.toString())



        println("List to json - extension   ")

        var moshi = Moshi.Builder().build()


        var listToJson = moshi.listToJson(list)

        println("List to json - $listToJson")


        println(" json to list - extension   ")

        var jsonToList = moshi.jsonToList<SingleListItem>(listToJson)

        println("json to list  - $jsonToList")


        //json to object
        println("json to object  -")

        var singleListItem = SingleListItem("title1", 1)
        var objectToJson = moshi.objectToJson<SingleListItem>(singleListItem)

        println("json to object  - $objectToJson")


        //object to json
        println("objecttoJson  -")

        var objecttoJson = objectToJson?.let { moshi.jsonToObject<SingleListItem>(it) }

        println("objecttoJson  - $objecttoJson")


        // creating object based on item from json value


        var parent = User.Parent("parent", "parentName", "occP", "salary", "a")
        var child = User.Child("", "child", "childName", "occP", "salary",1)


        var listOfuser = mutableListOf<User>(parent, child)

        var moshiPolyMorphic = Moshi.Builder().add(
            PolymorphicJsonAdapterFactory.of(User::class.java, "type")
                .withSubtype(User.Parent::class.java, "parent")
                .withSubtype(User.Child::class.java, "child")
        )
            .build()

        var listToJson1 = moshiPolyMorphic.listToJson(listOfuser)

        println("listToJson1  - $listToJson1")


        var jsonToListOfUsers = moshiPolyMorphic.jsonToList<User>(listToJson1)

        println("jsonToListOfUsers  - $jsonToListOfUsers")




        println("json object check")

        //json to object


        var checkMoshiBuilder = Moshi.Builder().add(JSONObjectAdapter()).build()


        var singleListItemCheck = SingleListItem("title1", 1)
        var objectToJsonCheck = checkMoshiBuilder.objectToJson<SingleListItem>(singleListItemCheck)


        var singleListItemCheck2 = SingleListItem("title2", 5)
        var objectToJsonCheck2 =
            checkMoshiBuilder.objectToJson<SingleListItem>(singleListItemCheck2)


        var jsonObject1 = objectToJsonCheck?.let {
            JSONObject(objectToJsonCheck)
        }
        var jsonObject2 =

            objectToJsonCheck2?.let {
                JSONObject(objectToJsonCheck2)
            }


        if (jsonObject1?.has("title") == true) {
            println("json object check - $jsonObject1")
        }


        if (jsonObject2 != null) {
            if (jsonObject2.has("aa")) {
                println("json object check aa true- $jsonObject2")
            } else {
                println("json object check aa false- $jsonObject2")

            }
        }


        //custom adapter for open class

        var newParent = User.Parent("Text", "parent", "parentName", "occP", "salary")
        var newchild = User.Child("child", "child", "childName", "occP", "salary",1)


        var json =
//            "[{\"type\":\"parent\",\"text\":\"parent\",\"name\":\"name\",\"occupation\":\"ocup\"},{\"type\":\"child\",\"gift\":\"parent\",\"name\":\"childName\",\"occupation\":\"ocup\"}]"
            "{\"gift\":null,\"name\":\"name\",\"occupation\":\"ocup\",\"salary\":\"sal\"}"

//        "parent"
//        "type":"parent",
//        "type":"null",
//        "number":"1"
//        ,"number":null

        var cusAdapter = UserClassAdapter()

        var newMoshi = Moshi.Builder().add(cusAdapter).build()

        var listOf = listOf(newParent, newchild)

        var objectToCusJson = newMoshi.listToJson<User>(listOf)

        println("json object custom adapter listtojson- $objectToCusJson")
        println("json object custom adapter listOf list- $listOf")


//        var jsonToListUser = newMoshi.jsonToList<User>(objectToCusJson)


        println("json object custom adapter  json data - $json")
//        var jsonToObjectUser = newMoshi.jsonToList<User>(json)
//
//        println("json to user class- $jsonToObjectUser")

//        var fromJsonUser = cusAdapter.fromJson(json)


//        println("json to user class object- ${fromJsonUser}")'


//        var jsonToObject = newMoshi.jsonToObject<User>(json)
//
//
//        println("json to user class object- ${jsonToObject.toString()}")



        var gson = Gson()
        val fromJsonGson = gson.fromJson<User.Child>(json, User.Child::class.java)

        println("json to user class object child GSON - ${fromJsonGson}")

        var moshiAdap = Moshi.Builder().build()

//        var jsonAdapter : JsonAdapter<User.Child> = moshiAdap.adapter<User.Child?>(User.Child::class.java).lenient()
//
//        println("json to user class object child- ${jsonAdapter.fromJson(json)}")



        val childMoshi = Moshi.Builder().build()

        val jsonToObject = childMoshi.jsonToObject<User.Child>(json)
        println("json to user class object- ${jsonToObject}")











//        jsonToObject.let {
//            it
//        }


    }

    open class Content {

    }

    data class Text(val name: String) : Content()
    data class Id(val id: Int) : Content()


    @Parcelize
    open class User() : Parcelable {
        @Parcelize
        @JsonClass(generateAdapter = true)
        data class Parent(
            var text: String,
            var type: String,
            var name: String,
            var occupation: String,
            var salary: String
        ) : User(), Parcelable


        @Parcelize
        @JsonClass(generateAdapter = true)
        data class Child(
            var gift: String? = null,
            var type: String = "aa",
            var name: String,
            var occupation: String,
            var salary: String,
            val number:Int = 0
        ) : User(), Parcelable

        override fun toString(): String {
            return super.toString()
        }
    }

/*
internal class UserAdapter {
    // Note that you pass in a `UserJson` object here


    @FromJson
    fun fromJson(userJson: UserJson): User? {
        return when (userJson.type) {
            "Parent" -> {
                val parent = User.Parent()
                parent.type = userJson.type
                parent.name = userJson.name
                parent.occupation = userJson.occupation
                parent.salary = userJson.salary
                parent
            }
            "Child" -> {
                val child = User.Child()
                child.type = userJson.type
                child.name = userJson.name
                child.favoriteToy = userJson.favorite_toy
                child.grade = userJson.grade
                child
            }
            else -> null
        }
    }

    // Note that you return a `UserJson` object here.
    @ToJson
    fun toJson(user: NameClass): UserJson {
        val json = UserJson()
        if (user is Parent) {
            json.type = "Parent"
            json.occupation = (user as Parent).occupation
            json.salary = (user as Parent).salary
        } else {
            json.type = "Child"
            json.favorite_toy = (user as Child).favoriteToy
            json.grade = (user as Child).grade
        }
        json.name = user.name
        return json
    }
}*/


    /*class CustomCLassAdapter {
        @FromJson
        fun fromJson(json: JSONObject): User? {
            var moshi = Moshi.Builder().build()
            if (json.has("text")) {
                moshi.jsonToObject<User.Parent>(json)
                return User.Parent(json)
            }
        }

        @ToJson
        fun toJson(user: User): String {

        }

    }*/


    class JSONObjectAdapter {

        @FromJson
        fun fromJson(reader: JsonReader): JSONObject? {
            // Here we're expecting the JSON object, it is processed as Map<String, Any> by Moshi

            reader.isLenient = true
            return (reader.readJsonValue() as? Map<String, Any>)?.let { data ->
                try {
                    var jsonObject = JSONObject(data)
                    jsonObject

                } catch (e: JSONException) {
                    // Handle exception
                    return null
                }
            }
        }

        @ToJson
        fun toJson(writer: JsonWriter, value: JSONObject?) {
            writer.isLenient = true
            value?.let { writer.value(Buffer().writeUtf8(value.toString())) }
        }
    }
}


class UserClassAdapter {

    @FromJson
//    fun fromJson(json: String): GenericExamples.User {
    fun fromJson(reader: JsonReader): GenericExamples.User? {
        // Here we're expecting the User, it is processed as Map<String, Any> by Moshi

/*
        var jsonObject1 = JSONObject(json)

        var moshi = Moshi.Builder().build()
        if (jsonObject1.has("text")) {

            moshi.jsonToObject<GenericExamples.User.Parent>(jsonObject1.toString())

        } else if (jsonObject1.has("gift")) {
            moshi.jsonToObject<GenericExamples.User.Child>(jsonObject1.toString())
        }
        return GenericExamples.User()
*/




        /*reader.isLenient = true
        return (reader.readJsonValue() as? Map<String, Any>)?.let { data ->
            try {
                var jsonObject = JSONObject(data)

                if(jsonObject.has("text")) {

                } else if(jsonObject.has("gift")) {

                }




            } catch (e: JSONException) {
                // Handle exception
                return null
            }
        }*/

        reader.isLenient = true
        return (reader.readJsonValue() as? Map<String, Any>)?.let { data ->
            try {
                val jsonObject = JSONObject(data)

                var moshi = Moshi.Builder().build()
                if (jsonObject.has("text")) {

                    var jsonToObjectParent =
                        moshi.jsonToObject<GenericExamples.User.Parent>(jsonObject.toString())

                    println("json to user class object parent- $jsonToObjectParent")

                    return@let jsonToObjectParent
                } else if (jsonObject.has("gift")) {
                    var jsonToObject =
                        moshi.jsonToObject<GenericExamples.User.Child>(jsonObject.toString())

                    println("json to user class object child- $jsonToObject")

                    return@let jsonToObject

                }
                GenericExamples.User()
            } catch (e: JSONException) {
//                 Handle exception
                null
            }
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: GenericExamples.User?) {
//        var moshi = Moshi.Builder().build()
//        moshi.objectToJsonObject<GenericExamples.User>(writer)

        value?.let {
            writer.value(Buffer().writeUtf8(value.toString()))
        }
    }
}


inline fun <reified T> Moshi.listToJson(data: List<T>): String =
    adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java))
        .toJson(data)


inline fun <reified T> Moshi.jsonToList(json: String): List<T>? =
    adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java))
        .fromJson(json)

inline fun <reified T, reified K> Moshi.mapToJson(map: Map<T, K>): String? =
    adapter<Map<T, K>>(
        Types.newParameterizedType(
            Map::class.java,
            T::class.java,
            K::class.java
        )
    )
        .toJson(map)

inline fun <reified T, reified K> Moshi.jsonToMap(json: String): Map<T, K>? =
    adapter<Map<T, K>>(
        Types.newParameterizedType(
            Map::class.java,
            T::class.java,
            K::class.java
        )
    )
        .fromJson(json)


inline fun <reified T> Moshi.objectToJson(data: T): String? =
    adapter<T>(T::class.java).toJson(data)

inline fun <reified T> Moshi.jsonToObject(data: String?): T? =
    data?.let { adapter<T>(T::class.java).fromJson(data) }
//    data?.let { adapter<T>(T::class.java).lenient().fromJson(data) }


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


class DefaultOnDataMismatchAdapter<T> private constructor(
    private val delegate: JsonAdapter<T>,
    private val defaultValue: T
) :
    JsonAdapter<T?>() {
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): T? {
        // Read the value first so that the reader will be in a known state even if there's an
        // exception. Otherwise it may be awkward to recover: it might be between calls to
        // beginObject() and endObject() for example.
        val jsonValue = reader.readJsonValue()

        // Use the delegate to convert the JSON value to the target type.
        return try {
            delegate.fromJsonValue(jsonValue)
        } catch (e: JsonDataException) {
            defaultValue
        }
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: T?) {
        delegate.toJson(writer, value)
    }

    companion object {
        fun <T> newFactory(type: Class<T>, defaultValue: T): Factory {
            return object : Factory {
                override fun create(
                    requestedType: Type, annotations: Set<Annotation?>?, moshi: Moshi
                ): JsonAdapter<*>? {
                    if (type != requestedType) return null
                    val delegate: JsonAdapter<T> = moshi.nextAdapter(this, type, annotations)
                    return DefaultOnDataMismatchAdapter(delegate, defaultValue)
                }
            }
        }
    }
}