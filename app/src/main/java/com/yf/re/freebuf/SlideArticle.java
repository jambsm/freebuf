package com.yf.re.freebuf;

/**
 * Created by lenovo on 2016/12/30.
 */

public class SlideArticle {
    public String title;
    public String linkUrl;
    public String imageUrl;

    public SlideArticle(String imageUrl, String linkUrl, String title) {
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
        this.title = title;
    }
}
