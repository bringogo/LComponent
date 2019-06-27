package com.lb.module

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lb.component.LComponent
import kotlinx.android.synthetic.main.layout_a_module_view.view.*

/**
 *
 * @author libo
 * @date 2019/06/27
 */
class AModuleView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), LComponent {

    private var eventListener: LComponent.IEventListener? = null

    private var gson: Gson = GsonBuilder().create()

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_a_module_view, this, true)

        // show onEvent can be applied in varies ways
        btn.setOnClickListener {
            eventListener?.onEvent(gson.toJson(EventData().apply {
                type = "button-click"; value = btn.text.toString()
            }))
        }
        btn.setOnLongClickListener {
            eventListener?.onEvent(gson.toJson(EventData().apply {
                type = "button-long-click"; value = btn.text.toString()
            }))
            true
        }
        et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                eventListener?.onEvent(gson.toJson(EventData().apply {
                    type = "edittext-afterTextChanged"; value = et.text.toString()
                }))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    /** data definitions start------------- */
    class ViewData {
        var buttonData: String? = null
        var edittextData: String? = null
    }

    class EventData {
        var type: String? = null
        var value: String? = null
    }

    /** data definitions end------------- */

    override fun updateData(jsonData: String): String {
        val data = gson.fromJson<ViewData>(jsonData, ViewData::class.java)
        btn.text = data.buttonData
        et.setText(data.edittextData)
        return ""
    }

    override fun obtainValue(type: String) = et.text.toString()

    override fun setupEventListener(eventListener: LComponent.IEventListener?) {
        this.eventListener = eventListener
    }
}