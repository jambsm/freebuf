package com.yf.re.freebuf;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.widget.TextView;

import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by lenovo on 2017/3/24.
 */

public class HtmlTextView extends TextView {
    public static final String TAG="HtmlTextView";
    public static final boolean DEBUG=false;

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HtmlTextView(Context context) {
        super(context);
    }

     private static String convertStreamToString(InputStream in){
        java.util.Scanner s=new java.util.Scanner(in).useDelimiter("\\A");
         return s.hasNext()?s.next():"";
    }

    public void  setHtmlFromString(String html,boolean useLocalDrawables){
        Html.ImageGetter imageGetter;
       imageGetter=new articleImageGetter(this,getContext());

        setText(Html.fromHtml(html, imageGetter, new Html.TagHandler() {
            @Override
            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

            }
        }));
        setMovementMethod(LinkMovementMethod.getInstance());
       /*
        setAutoLinkMask(Linkify.WEB_URLS);
        CharSequence text=getText();
        if(text instanceof Spannable){
            int end=text.length();
            Spannable sp=(Spannable)getText();
            URLSpan [] urls=sp.getSpans(0,end,URLSpan.class);
            SpannableStringBuilder style=new SpannableStringBuilder(text);
            style.clearSpans();
            for(URLSpan url:urls){
                articleClickSpan articlespan=new articleClickSpan(url.getURL());
                style.setSpan(articlespan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            setText(style);

        }*/
    }

}
