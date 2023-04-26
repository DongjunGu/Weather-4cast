/*
 * Weather4cast
 * Robert Russell | Dongjun Gu
 * April/2023
 */
package com.mobileapp.a4cast;

public class DatabaseItem {
    private int id;
    private String type;
    private String name;
    private int minTemp;
    private int maxTemp;
    private String conditions;
    private String link;
    private String recipe;
    private String comment;

    public DatabaseItem() {
        // Empty constructor
    }

    public DatabaseItem(int id, String type, String name, int minTemp, int maxTemp, String conditions, String link, String recipe, String comment) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.conditions = conditions;
        this.link = link;
        this.recipe = recipe;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String printItemInfo() {
        return "-------\n" +
                "TYPE: " + this.getType() +
                "\nNAME: " + this.getName() +
                "\nMIN_TEMP: " + this.getMinTemp() +
                "\nMAX_TEMP: " + this.getMaxTemp() +
                "\nCONDITIONS: " + this.getConditions() +
                "\nLINK: " + this.getLink() +
                "\nRECIPE: " + this.getRecipe() +
                "\nCOMMENT: " + this.getComment() +
                "\n-------";
    }
}