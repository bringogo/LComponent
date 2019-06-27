package com.lb.component

/**
 * [LComponent] defines interfaces for a component to update data with, obtain value from and notify
 * events, which are defined by 3 methods and 1 interface.
 *
 * A component can be a simple component for example a Java class, or complex android custom view
 * for instance.
 *
 * Component may not be isolated from others, it may receive data to get updated, return value if
 * others want, notify events to others.Moreover some times or in some situations, the specific
 * class type of a component can not be determined which leads to failing in connection with it.
 *
 * But if a component implements the LComponent, the component becomes available and friendly.
 *
 * @author libo
 * @date 2019/6/27
 */
interface LComponent {
    /**
     * Update component with [jsonData] and return json-format string result.
     *
     * [jsonData] is json-format string which can be flexible to fit all data requirement,
     * the concrete format must be negotiated between both sides, the result data goes same as well.
     */
    fun updateData(jsonData: String): String

    /**
     * Obtain component's json-format value of [type].
     *
     * the returned value is json-format string which can be flexible to fit all value requirement.
     */
    fun obtainValue(type: String): String

    /**
     * Setup event listener to lister to all kind of events component could notify.
     *
     * [eventListener] can be null which means the former event listener should be cleared.
     */
    fun setupEventListener(eventListener: IEventListener?)

    /**
     * Event listener of all events that component can notify
     */
    interface IEventListener {
        /**
         * Got event in format of json notified by component
         */
        fun onEvent(jsonEvent: String)
    }
}