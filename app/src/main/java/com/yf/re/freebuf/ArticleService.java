package com.yf.re.freebuf;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lenovo on 2016/11/21.
 */

public class ArticleService {
    ArrayList<Article> articles;
    ArrayList<SlideArticle> slideArticles;
    Article article;
    SlideArticle slideArticle;
    //获取新闻列表
    public ArrayList<Article> getArticles(String sUrl){


        URL url=null;
        HttpURLConnection httpURLConnection=null;
        articles=new ArrayList<Article>();
        try{
        url=new URL(sUrl);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200){
                String html=getStreamToString(httpURLConnection.getInputStream());
                Document doc= Jsoup.parse(html);
                Elements article_list=doc.select("div[class=news_inner news-list]");

                for(Element single_article:article_list){
                   Elements content=single_article.select("dd[class=text]");
                    Elements titles=single_article.select("a[target=_blank]");
                    Elements img_div=single_article.select("div[class=news-img]");
                    Elements im=img_div.select("img");
                    Elements images=img_div.select("img");
                    Elements authors=single_article.select("span[class=name-head]");
                    Elements authors1=single_article.select("span[class=name]");
                    Elements times=single_article.select("span[class=time]");
                    Elements links=single_article.select("a[target=_blank]");

                    Log.e("Content:",content.text());
                    Log.e("title:",titles.text());
                    Log.e("time::",times.text());
                    Log.e("author:",authors.text());
                    Log.e("author1",authors1.text());
                    Log.e("Images:",images.attr("src"));
                    Log.e("Images1:",img_div.toString());
                    Log.e("im:",im.toString());
                    Log.e("links:",links.attr("href"));

                    String Sname;
                    if(authors.text()!=""){
                        Sname=authors.text();
                    }
                    else
                    {
                        Sname=authors1.text();
                    }

                    String Scontent=content.text();
                    String Stitle=titles.text();
                    String Stime=times.text();
                    String Simage=images.attr("src");
                    String Slink=links.attr("href");
                    article=new Article(Sname,Scontent,Simage,Stime,Stitle,Slink);
                    articles.add(article);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return articles;
    }
//获取新闻详细内容
    public Article getArticleDetail(String surl) {
        Log.e("aaaaa", surl);
        URL url = null;
        Article article=null;
        HttpURLConnection httpURLConnection = null;
        try {

            url = new URL(surl);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {

                String html = getStreamToString(httpURLConnection.getInputStream());
                Document doc = Jsoup.parse(html);
                Elements article_content = doc.select("div[class=articlecontent]");
                Elements article_titles = article_content.select("div[class=title]");
                Elements article_title = article_titles.select("h2");
                Elements article_auther = article_content.select("span[class=name]").select("a");
                Elements article_time = article_content.select("span[class=time]");
                Elements article_context = article_content.select("div[id=contenttxt]");


                Log.e("Author:", "" + article_auther.text());
                Log.e("time:", "" + article_time.text());
                Log.e("Context:", "" + article_context);
                Log.e("Title:",article_title.text());
                article=new Article(article_auther.text(),article_context.toString(),null,article_time.text(),article_title.text(),null);

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return article;
    }


  //滑动新闻列表服务，获取滑动新闻
    public ArrayList<SlideArticle> getSlideArticles(String sUrl){
        URL url=null;
        HttpURLConnection httpURLConnection=null;
        slideArticles=new ArrayList<SlideArticle>();
        try {
            url=new URL(sUrl);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200){
                String html=getStreamToString(httpURLConnection.getInputStream());
                Document doc= Jsoup.parse(html);
                Elements article_list=doc.select("div[class=item]");

                for(Element single_article:article_list){

                    Elements titleDiv=single_article.select("div[class=carousel-caption]");
                    Elements title=titleDiv.select("a");
                    Elements imgUrl=single_article.select("img");
                    Log.e("slide href",title.attr("href"));
                   // Log.e("slide title",title.text());
                   // Log.e("Slide img",imgUrl.attr("src"));
                    slideArticle=new SlideArticle(imgUrl.attr("src"),title.attr("href")+"",title.text()+"");
                    slideArticles.add(slideArticle);

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  slideArticles;
    }
    public String getStreamToString(InputStream in){
        BufferedReader reader=null;
        StringBuilder sb=new StringBuilder();
        String line=null;
        try {
            reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
            while ((line=reader.readLine())!=null){
                sb.append(line);
                sb.append("\r\n");
            }
            in.close();
        }catch (Exception e){

        }

        return sb.toString();
    }
}
