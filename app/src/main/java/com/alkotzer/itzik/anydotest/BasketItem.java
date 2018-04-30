package com.alkotzer.itzik.anydotest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itzikalkotzer on 30/04/2018.
 */

public class BasketItem
{
    /**
     * name : Potatoes
     * weight : 1.8kg
     * bagColor : #F191BA
     */

    @SerializedName("name")
    private String mName;
    @SerializedName("weight")
    private String mWeight;
    @SerializedName("bagColor")
    private String mBagColor;

    public String getName()
    {
        return mName;
    }

    public String getWeight()
    {
        return mWeight;
    }

    public String getBagColor()
    {
        return mBagColor;
    }

}
