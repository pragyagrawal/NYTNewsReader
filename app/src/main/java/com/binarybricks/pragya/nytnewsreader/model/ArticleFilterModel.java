package com.binarybricks.pragya.nytnewsreader.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by PRAGYA on 10/22/2016.
 */

public class ArticleFilterModel implements Parcelable {

    private String beginDate;
    private String sortOrder;
    private ArrayList<String> newsDeskValue;

    public ArticleFilterModel() {
    }

    protected ArticleFilterModel(Parcel in) {
        beginDate = in.readString();
        sortOrder = in.readString();
        if (in.readByte() == 0x01) {
            newsDeskValue = new ArrayList<String>();
            in.readList(newsDeskValue, String.class.getClassLoader());
        } else {
            newsDeskValue = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(beginDate);
        dest.writeString(sortOrder);
        if (newsDeskValue == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(newsDeskValue);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ArticleFilterModel> CREATOR = new Parcelable.Creator<ArticleFilterModel>() {
        @Override
        public ArticleFilterModel createFromParcel(Parcel in) {
            return new ArticleFilterModel(in);
        }

        @Override
        public ArticleFilterModel[] newArray(int size) {
            return new ArticleFilterModel[size];
        }
    };

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public ArrayList<String> getNewsDeskValue() {
        return newsDeskValue;
    }

    public void setNewsDeskValue(ArrayList<String> newsDeskValue) {
        this.newsDeskValue = newsDeskValue;
    }
}
