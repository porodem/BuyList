package com.porodem.buylist;

import android.support.annotation.NonNull;

import java.util.UUID;

public class Product {

    private String mName;
    private UUID mUUID;
    private String mType;


    public Product() {
        this(UUID.randomUUID());
    }

    public Product(UUID id){
        mUUID = id;
        mName = "something";
        mType = ""; //lol
    }

    public Product(UUID id, String name, String type) {
        mUUID = id;
        mName = name;
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
