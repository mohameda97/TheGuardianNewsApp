package com.example.theguardiannewsapp;

public class News {
    private final String mType;
    private final String mSectionName;
    private final String mTitle;
    private final String mUrl;
    private final String mDate;
    private final String mAuthorName;

    public News(String mType, String mSectionName, String mTitle, String mUrl, String mDate,String mAuthorName) {
        this.mType = mType;
        this.mSectionName = mSectionName;
        this.mTitle = mTitle;
        this.mUrl = mUrl;
        this.mAuthorName = mAuthorName;
        this.mDate = mDate;
    }

    public String getType() {
        return mType;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public String getDate() {
        return mDate;
    }
}
