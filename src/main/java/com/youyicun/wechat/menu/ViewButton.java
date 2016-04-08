package com.youyicun.wechat.menu;

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

    public ViewButton(String type,String name,String url){
        super(type,name);
        this.url = url;
    }



    public ViewButton(){}
}
