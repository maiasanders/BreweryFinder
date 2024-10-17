package com.techelevator.model;

public class Style {
    private int styleId;
    private String styleName;

    public Style() {}

    public Style(int styleId, String styleName) {
        this.styleId = styleId;
        this.styleName = styleName;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
}
