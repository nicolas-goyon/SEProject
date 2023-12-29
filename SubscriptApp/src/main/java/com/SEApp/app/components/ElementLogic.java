package com.SEApp.app.components;

/**
 * This class is used to display an element of a list
 */
public class ElementLogic {

    private int id;
    private String title;
    private String description;

    /**
     * Constructor
     * @param title
     * @param description
     */
    public ElementLogic(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
