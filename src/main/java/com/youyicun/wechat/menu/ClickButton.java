package com.youyicun.wechat.menu;

/**
 * Created by johnny on 16/4/4.
 */
public class ClickButton extends Button {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ClickButton(String type, String name, String key) {
        super(type, name);
        this.key = key;
    }

    public ClickButton(){}
}
