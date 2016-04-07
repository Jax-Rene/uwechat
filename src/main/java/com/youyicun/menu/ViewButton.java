package com.youyicun.menu;

/**
 * Created by johnny on 16/4/4.
 */
public class ViewButton extends Button{
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ViewButton(String type, String name, Button[] sub_button, String url) {
        super(type, name, sub_button);
        this.url = url;
    }

    public ViewButton(){}
}
