package com.lb.app

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lb.component.LComponent
import java.io.Serializable

/**
 *
 *
 * @author libo
 * @date 2019/6/27
 */
class AppComponent : LComponent {
    private var stringField: String? = null
    private var customField: CustomData? = null
    private var serializableField: SerializableData? = null
    private var parcelableField: ParcelableData? = null

    private var eventListener: LComponent.IEventListener? = null

    private var gson: Gson = GsonBuilder().create()


    /** data definitions start------------- */
    class CustomData {
        var field: String? = null

        override fun toString(): String {
            return field ?: ""
        }
    }

    class SerializableData : Serializable {
        var field1: String? = null
        var field2: String? = null

        override fun toString(): String {
            return "$field1,$field2"
        }
    }

    class ParcelableData() : Parcelable {
        var field11: String? = null
        var field12: Int = 0

        constructor(parcel: Parcel) : this() {
            field11 = parcel.readString()
            field12 = parcel.readInt()
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeString(field11)
            dest?.writeInt(field12)
        }

        override fun describeContents() = 0

        companion object CREATOR : Parcelable.Creator<ParcelableData> {
            override fun createFromParcel(parcel: Parcel): ParcelableData {
                return ParcelableData(parcel)
            }

            override fun newArray(size: Int): Array<ParcelableData?> {
                return arrayOfNulls(size)
            }
        }

        override fun toString(): String {
            return "$field11,$field12"
        }
    }

    /**
     * incoming data structure
     */
    class Data {
        var stringField: String? = null
        var customField: CustomData? = null
        var serializableField: SerializableData? = null
        var parcelableField: ParcelableData? = null
    }

    class EventData {
        var type: String? = null
        var value: String? = null
    }

    /** data definitions end------------- */


    override fun updateData(jsonData: String): String {
        val data = gson.fromJson<Data>(jsonData, Data::class.java)
        stringField = data.stringField
        customField = data.customField
        serializableField = data.serializableField
        parcelableField = data.parcelableField
        return this.toString()
    }

    override fun obtainValue(type: String): String = when (type) {
        "getBasic" -> stringField ?: ""
        "getCustom" -> gson.toJson(customField)
        "getSerializable" -> gson.toJson(serializableField) + "@@" + gson.toJson(parcelableField)
        else -> this.toString()
    }

    fun biteMe() {
        eventListener?.onEvent(gson.toJson(EventData().apply {
            type = "bite"; value = "I bite back ()-:"
        }))
    }

    fun tasteMe() {
        eventListener?.onEvent(gson.toJson(EventData().apply {
            type = "taste"; value = "sorry, are you sick ???"
        }))
    }

    override fun setupEventListener(eventListener: LComponent.IEventListener?) {
        this.eventListener = eventListener
    }


    override fun toString(): String {
        return "stringField:$stringField, customField:$customField, serializableField:$serializableField" +
                ", parcelableField:$parcelableField"
    }
}