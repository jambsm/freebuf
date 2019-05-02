package com.yf.re.freebuf;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;



public class articleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<Article> articles;
    public int layoutId;
    LayoutInflater inflater;
    OnItemClickListener  mListener;
    private static final int TYPE_ITEM=0;
    private  static final int TYPE_FOOTER=1;
    OnLoadMore onLoad;
    Context context;
    public articleAdapter(ArrayList<Article> articles, int layoutId, Context context) {
        this.articles=articles;
        this.context=context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_ITEM){
            View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.articlelist,parent,false);
            articleViewHolder holder=new articleViewHolder(v,mListener);
           // holder.onClick(v);
            return holder;
        }else if(viewType==TYPE_FOOTER){
                View foot=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_foot,parent,false);
               footViewHolder holder=new footViewHolder(foot);
            return holder;
        }
        return null;


    }


    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if(holder instanceof articleViewHolder){
                Article article=articles.get(position);
                ((articleViewHolder)holder).articleTitle.setText(article.Titel);
                ((articleViewHolder)holder).articleAuther.setText(article.Auther);
                ((articleViewHolder)holder).articleTime.setText(article.Time);
                ((articleViewHolder)holder).articleContent.setText(article.Content);
                if(mListener!=null){
                    ((articleViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onItemClick(v,position);
                        }
                    });
                }
                ImageLoader imageLoader=ImageLoader.getInstance();
                imageLoader.displayImage(article.FirstImage,((articleViewHolder)holder).articleImage);
            }else if(holder instanceof footViewHolder){
                ((footViewHolder) holder).foottext.setText("正在加载");
            }



    }

    @Override
    public int getItemViewType(int position) {
        Log.i("aaa",position+"");
       if(getItemCount()==position+1){
           return TYPE_FOOTER;
       }else
           return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {

        Log.e("aaaaaaaaaaa",articles.size()+"");
        return articles.size()+1;
    }

    interface OnItemClickListener{
        public void onItemClick(View v,int pos);
    }
     interface  OnLoadMore{
      public   void LoadMore();
    }
    public void setItemActionListener(OnItemClickListener listener){
        this.mListener=listener;
    }

    public void SetOnLoadMoreListener(OnLoadMore onLoadMoreloadMore){
     this.onLoad=onLoadMoreloadMore;
    }



    public static class articleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView articleImage;
        TextView articleTitle;
        TextView articleTime;
        TextView articleAuther;
        TextView articleContent;
        public OnItemClickListener Listener;
        public articleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            articleImage=(ImageView)itemView.findViewById(R.id.article_image);
            articleAuther=(TextView)itemView.findViewById(R.id.article_auther);
            articleTime=(TextView)itemView.findViewById(R.id.article_time);
            articleTitle=(TextView)itemView.findViewById(R.id.article_title);
            articleContent=(TextView)itemView.findViewById(R.id.article_content);
            Listener=listener;
        }

        @Override
        public void onClick(View v) {
            Listener.onItemClick(v,getPosition());
        }
    }

    public static class footViewHolder extends RecyclerView.ViewHolder{
        private  TextView foottext;
        public footViewHolder(View itemView) {
            super(itemView);
            foottext=(TextView)itemView.findViewById(R.id.getmore);
        }
    }

}
