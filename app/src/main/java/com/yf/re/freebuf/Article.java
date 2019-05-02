package com.yf.re.freebuf;

import android.graphics.drawable.Drawable;

/**
 * Created by lenovo on 2016/11/21.
 */

public class Article {
    public String Titel;
    public String Time;
    public String Auther;
    public String Content;
    public String FirstImage;
    public String LinkPage;

    public Article(String auther, String content, String firstImage, String time, String titel,String linkPage) {
        Auther = auther;
        Content = content;
        FirstImage = firstImage;
        Time = time;
        Titel = titel;
        this.LinkPage=linkPage;
    }
}
