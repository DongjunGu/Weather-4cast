/*
 * Weather4cast
 * Robert Russell | Dongjun Gu
 * April/2023
 */
package com.mobileapp.a4cast;

public class ModelClass {

    private int imageview1;
    private String textview1, itemLink, itemRecipe, itemComment;

    public ModelClass(int imageview1, String textview1, String link, String recipe, String comment) {
        this.imageview1 = imageview1;
        this.textview1 = textview1;
        this.itemLink = link;
        this.itemRecipe = recipe;
        this.itemComment = comment;
    }

    public int getImageview1() {
        return imageview1;
    }

    public String getTextview1() {
        return textview1;
    }

    public String getItemLink() {
        if (itemLink == null) {
            return "No Link";
        } else {
            return itemLink;
        }
    }

    public String getItemRecipe() {
        if (itemRecipe == null) {
            return "No Recipe";
        } else {
            return itemRecipe;
        }
    }

    public String getItemComment() {
        if (itemComment == null) {
            return "No Comment";
        } else {
            return itemComment;
        }
    }
}
