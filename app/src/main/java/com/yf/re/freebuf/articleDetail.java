package com.yf.re.freebuf;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lenovo on 2017/3/19.
 */

public class articleDetail extends AppCompatActivity {

    Article article;
    ArticleService articleService;
    articleDetailAsyncTask asyncTask;
    TextView detail_title,detail_author,detail_time;
    HtmlTextView detail_context;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail);
        Intent intent=getIntent();
        String Position= intent.getStringExtra("Position");

        asyncTask=new articleDetailAsyncTask(this);
        asyncTask.execute(Position);

    }
    /*
public class articleDetailAsyncTask extends AsyncTask<String,Integer ,Article>{

    @Override
    protected Article doInBackground(String... params) {
       articleService=new ArticleService();
        article=articleService.getArticleDetail(params[0]);
        return article;
    }

    @Override
    protected void onPostExecute(Article article) {
        super.onPostExecute(article);
       detail_title=(TextView)findViewById(R.id.detail_title);
        detail_author=(TextView)findViewById(R.id.detail_author);
       detail_time=(TextView)findViewById(R.id.detail_time);
        detail_context=(HtmlTextView) findViewById(R.id.detail_content);
        detail_title.setText(article.Titel);
       detail_author.setText(article.Auther);
       detail_time.setText(article.Time);
        detail_context.setHtmlFromString(article.Content,false);

    }
}
*/

}
