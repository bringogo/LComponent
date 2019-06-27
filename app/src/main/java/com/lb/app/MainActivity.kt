package com.lb.app

import android.app.Activity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lb.component.LComponent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    private var gson: Gson = GsonBuilder().create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testAppComponent()
        testModuleComponent()
    }

    private fun testAppComponent() {
        tvTestAppComponent.movementMethod = ScrollingMovementMethod.getInstance()
        tvTestAppComponent.text = ""

        val appComponent = AppComponent()
        val lComponent = appComponent as LComponent

        val data = AppComponent.Data().apply {
            stringField = "hi"
            customField = AppComponent.CustomData().apply {
                field = "guys"
            }
            serializableField = AppComponent.SerializableData().apply {
                field1 = "this is"
                field2 = "a sample of"
            }
            parcelableField = AppComponent.ParcelableData().apply {
                field11 = "testing lComponent"
                field12 = 100
            }
        }
        val jsonData = gson.toJson(data)
        tvTestAppComponent.text = "updateData:\n$jsonData"
        val result = lComponent.updateData(jsonData)
        tvTestAppComponent.text = "${tvTestAppComponent.text}\nresult:\n$result"

        val basicValue = lComponent.obtainValue("getBasic")
        val customValue = lComponent.obtainValue("getCustom")
        val serializableValue = lComponent.obtainValue("getSerializable")
        tvTestAppComponent.text = "${tvTestAppComponent.text}\n\nget basicValue:$basicValue\ncustomValue:$customValue\nserializableValue:$serializableValue"

        lComponent.setupEventListener(object: LComponent.IEventListener {
            override fun onEvent(jsonEvent: String) {
                tvTestAppComponent.text = "${tvTestAppComponent.text}\nonEvent:$jsonEvent"
            }
        })
        tvTestAppComponent.text = "${tvTestAppComponent.text}\n\nBite component: :-()"
        appComponent.biteMe()
        tvTestAppComponent.text = "${tvTestAppComponent.text}\nTaste component: :-#"
        appComponent.tasteMe()
    }


    private fun testModuleComponent() {
        tvTestModuleComponent.movementMethod = ScrollingMovementMethod.getInstance()
        tvTestModule.setOnClickListener {
            // just for better ui looking
            tvTestAppComponent.text = ""
        }

        tvTestModuleComponent.text = ""
        val lComponent = amv as LComponent

        val jsonData = gson.toJson(ModuleViewData().apply {
            buttonData = "click me"
            edittextData = "hello"
        })
        tvTestModuleComponent.text = "updateData:\n$jsonData"
        lComponent.updateData(jsonData)

        val value = lComponent.obtainValue("")
        tvTestModuleComponent.text = "${tvTestModuleComponent.text}\n\nobtainValue:\n$value"

        lComponent.setupEventListener(object: LComponent.IEventListener {
            override fun onEvent(jsonEvent: String) {
                tvTestModuleComponent.text = "${tvTestModuleComponent.text}\n\nonEvent:\n$jsonEvent"
            }
        })
    }
    /** Module data definitions start------------- */
    class ModuleViewData {
        var buttonData: String? = null
        var edittextData: String? = null
    }
    /** Module data definitions end------------- */
}
