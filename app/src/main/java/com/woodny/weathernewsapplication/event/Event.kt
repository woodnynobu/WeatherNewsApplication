package com.woodny.weathernewsapplication.event

//　継承可能なクリック専用クラス
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    /**
     * contentを返却すると同時に、再度使用できないようにする
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * すでに処理されている場合でも、contentを返す
     */
    fun peekContent(): T = content
}