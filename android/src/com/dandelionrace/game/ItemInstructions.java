package com.dandelionrace.game;

import android.widget.ImageView;

public class ItemInstructions {
    private String name;
    private String explanation;

    public ItemInstructions(String name, String explanation, ImageView picture) {
        this.name = name;
        this.explanation = explanation;
        this.picture = picture;
    }

    private ImageView picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public ImageView getPicture() {
        return picture;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }
}
