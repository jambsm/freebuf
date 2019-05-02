package com.yf.re.freebuf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/12/11.
 */

public class new_fragment extends Fragment {
    ArticleService articleService;
    ArrayList<Article> articles;
    RecyclerView recycle;
    articleAsyncTask asyncTask;

    RecyclerView.LayoutManager mManager;
    static  int MorePage=2;
   static articleAdapter adapter;
    String sBasePath;

    public new_fragment() {
    }

    @SuppressLint("ValidFragment")
    public new_fragment(String sBasePath) {
        this.sBasePath = sBasePath;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.new_list,container,false);
        recycle=(RecyclerView)view.findViewById(R.id.new_article);
        mManager=new LinearLayoutManager(getContext());
        recycle.setLayoutManager(mManager);
        recycle.setItemAnimator(new DefaultItemAnimator());
        asyncTask=new articleAsyncTask();
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
                    moreasyncTask   moreArticles=new moreasyncTask();
                    //String base="http://www.freebuf.com/page/";
                    String path=sBasePath+"/page/"+MorePage+"";
                    MorePage++;
                    moreArticles.execute(path);

                    Log.e("articles.size()",articles.size()+"");
                    Log.e("MorePage",MorePage+"");

                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            adapter=new articleAdapter(articles,R.layout.articlelist,getContext());
            adapter.setItemActionListener(new articleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),articleDetail.class);

                    String linkUrl= articles.get(pos).LinkPage;
                    intent.putExtra("Position",linkUrl);
                    startActivity(intent);
                }
            });
            recycle.setAdapter(adapter);


        }
    }




}
