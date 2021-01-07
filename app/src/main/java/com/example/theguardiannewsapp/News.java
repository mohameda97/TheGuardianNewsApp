package com.example.theguardiannewsapp;

public class News {
    private String mType;
    private String mSectionName;
    private String mTitle;
    private String mUrl;
    private String mDate;

    public News(String mType, String mSectionName, String mTitle, String mUrl, String mDate) {
        this.mType = mType;
        this.mSectionName = mSectionName;
        this.mTitle = mTitle;
        this.mUrl = mUrl;
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

    public String getDate() {
        return mDate;
    }
}
