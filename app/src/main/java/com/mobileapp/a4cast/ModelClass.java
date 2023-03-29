package com.mobileapp.a4cast;

public class ModelClass {

    private int imageview1;
    private String textview1, itemLink;

    public ModelClass(int imageview1, String textview1, String link){
        this.imageview1=imageview1;
        this.textview1=textview1;
        this.itemLink=link;
    }

    public int getImageview1() {
        return imageview1;
    }

    public String getTextview1() {
        return textview1;
    }

    public String getItemLink() {
        return itemLink;
    }


}
