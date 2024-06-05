package com.example.testapp.data.question

class Item (picture: Int, title: String?, precent : String?, category: Int) {
    private var mPicture = 0
    private var mTitle: String? = null
    private var mPrecent: String? = null
    private var mCategory: Int = 0

    init {
        mPicture = picture
        mTitle = title
        mPrecent = precent
        mCategory = category
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

    fun getCategory(): Int {
        return mCategory
    }
}