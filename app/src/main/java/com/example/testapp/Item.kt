package com.example.testapp

class Item (picture: Int, title: String?, active: Boolean) {
    private var mPicture = 0
    private var mTitle: String? = null
    private var mActive = false

    init {
        mPicture = picture
        mTitle = title
        mActive = active
    }

    fun getPicture(): Int {
        return mPicture
    }

    fun getTitle(): String? {
        return mTitle
    }

    fun ismActive(): Boolean {
        return mActive
    }
}