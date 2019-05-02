package com.yf.re.freebuf;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/3/24.
 */

public class articleDetailAsyncTask extends AsyncTask<String,Integer ,Article> {
    Article article;
    ArticleService articleService;

    TextView detail_title,detail_author,detail_time;
    HtmlTextView detail_context;
    Activity activity;
    public articleDetailAsyncTask(Activity a) {
        activity=a;
    }

    @Override
    protected Article doInBackground(String... params) {
        articleService=new ArticleService();
        article=articleService.getArticleDetail(params[0]);
        return article;
    }

    @Override
    protected void onPostExecute(Article article) {
        super.onPostExecute(article);

        detail_title=(TextView)activity.findViewById(R.id.detail_title);
        detail_author=(TextView)activity.findViewById(R.id.detail_author);
        detail_time=(TextView)activity.findViewById(R.id.detail_time);
        detail_context=(HtmlTextView)activity.findViewById(R.id.detail_content);
        detail_title.setText(article.Titel);
        detail_author.setText(article.Auther);
        detail_time.setText(article.Time);
        detail_context.setHtmlFromString(article.Content,false);

    }

}
