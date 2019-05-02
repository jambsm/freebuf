package com.yf.re.freebuf;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/1/4.
 */

public class SubReadItemActivity extends AppCompatActivity {
    ArticleService articleService;
    ArrayList<Article> articles;
    RecyclerView recycle;
    articleAsyncTask asyncTask;

    RecyclerView.LayoutManager mManager;
    static  int MorePage=2;
    static articleAdapter adapter;
    String sBasePath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list);
        recycle=(RecyclerView)findViewById(R.id.new_article);
        mManager=new LinearLayoutManager(SubReadItemActivity.this);
        recycle.setLayoutManager(mManager);
        asyncTask=new articleAsyncTask();
        Intent intent=this.getIntent();
        sBasePath=intent.getStringExtra("path");
        asyncTask.execute(sBasePath);


        recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                lastVisibleItem=((LinearLayoutManager)mManager).findLastVisibleItemPosition();

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(lastVisibleItem+1==adapter.getItemCount()){
                    moreasyncTask moreArticles=new moreasyncTask();
                    //String base="http://www.freebuf.com/page/";
                    String path=sBasePath+"/page/"+MorePage+"";
                    MorePage++;
                    moreArticles.execute(path);

                    Log.e("articles.size()",articles.size()+"");
                    Log.e("MorePage",MorePage+"");

                }
            }
        });
    }


    public class moreasyncTask extends AsyncTask<String,Integer,ArrayList<Article>> {

        @Override
        protected ArrayList<Article> doInBackground(String... params) {
            ArticleService articleService=new ArticleService();
            ArrayList<Article> moreArticle=articleService.getArticles(params[0]);
            return moreArticle;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> morearticles) {
            super.onPostExecute(morearticles);
            articles.addAll(morearticles);
            adapter.notifyDataSetChanged();
        }
    }

    //异步加载文章列表
    public class articleAsyncTask extends AsyncTask<String,Integer,ArrayList<Article>>{

        @Override
        protected ArrayList<Article> doInBackground(String... params) {

            articleService=new ArticleService();
            articles=articleService.getArticles(params[0]);
            return articles;
        }

        @Override
        protected void onPostExecute(final ArrayList<Article> articles) {
            super.onPostExecute(articles);
            adapter=new articleAdapter(articles,R.layout.articlelist,SubReadItemActivity.this);
            adapter.setItemActionListener(new articleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    Intent intent=new Intent();
                    intent.setClass(SubReadItemActivity.this,articleDetail.class);

                    String linkUrl= articles.get(pos).LinkPage;
                    intent.putExtra("Position",linkUrl);
                    startActivity(intent);
                }
            });
            recycle.setAdapter(adapter);


        }
    }

}
