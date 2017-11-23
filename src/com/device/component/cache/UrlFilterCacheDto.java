package com.device.component.cache;

import java.io.Serializable;
import java.util.ArrayList;

public class UrlFilterCacheDto implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -2875269316231991091L;

    ArrayList<String> urls = new ArrayList<String>();

    public ArrayList<String> getUrls() {
        return this.urls;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

}
