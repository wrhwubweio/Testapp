package com.example.testapp

class Item (picture: Int, title: String?, precent : String?) {
    private var mPicture = 0
    private var mTitle: String? = null
    private var mPrecent: String? = null

    init {
        mPicture = picture
        mTitle = title
        mPrecent = precent
    }

    fun getPicture(): Int {
        return mPicture
    }

    fun getTitle(): String? {
        return mTitle
    }

    fun getPrecent(): String? {
        return mPrecent
    }
}