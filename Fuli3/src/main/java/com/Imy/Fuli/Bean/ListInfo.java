package com.Imy.Fuli.Bean;

/**
 * Created by user on 2015/11/23.
 */
public class ListInfo  {
    public String name;
    public String url;

    public ListInfo(String name, String icon) {
        this.name = name;
        this.url = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return url;
    }

    public void setIcon(String icon) {
        this.url = icon;
    }
}
