package com.yf.re.freebuf;

import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

/**
 * Created by lenovo on 2017/3/24.
 */

public class articleClickSpan extends ClickableSpan {
    private String uri;
    public articleClickSpan(String sUri) {
      uri=sUri;
    }

    @Override
    public void onClick(View widget) {
        Toast.makeText(widget.getContext(),uri,Toast.LENGTH_LONG).show();
    }
}
