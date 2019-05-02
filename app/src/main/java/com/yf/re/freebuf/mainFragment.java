package com.yf.re.freebuf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/12.
 */

@SuppressLint("ValidFragment") public class  mainFragment extends Fragment {
    ViewPager pager;
    PagerTabStrip tabStrip;
    ArrayList<Fragment> viewcontainer=new ArrayList<Fragment>();
    ArrayList<String> titlescontainer=new ArrayList<String>();
    ArrayList<SlideArticle> SlideArticle;
    View v;
    ViewFlipper viewFlipper;
    private List<View> mDotList;
    private final static int NUM=5;
    private  float x,y;
    private static  final  int AUTO=0X01;
    private static final int PREVIOUS=0x02;
    private static final int NEXT=0x03;
    ImageView SlideImage[];
    TextView SlideText[];
    SlideasyncTask asyncTask;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case AUTO:
                    showNext();
                    sendMsg();
                    break;
                case PREVIOUS:
                    mHandler.removeMessages(AUTO);
                    showPre();
                    sendMsg();
                    break;
                case NEXT:
                    mHandler.removeMessages(AUTO);
                    showNext();
                    sendMsg();
                    default:
                        break;
            }
        }
    };

    private void showNext() {
    viewFlipper.showNext();
        int current=viewFlipper.getDisplayedChild();
        if(current==0){
            mDotList.get(NUM-1).setBackgroundResource(R.drawable.dot_normal);
        }else {
            mDotList.get(current-1).setBackgroundResource(R.drawable.dot_normal);
        }
        mDotList.get(current).setBackgroundResource(R.drawable.dot_focus);

    }

    private void sendMsg() {
        Message msgs=new Message();
        msgs.what=AUTO;
        mHandler.sendMessageDelayed(msgs,2000);
    }

    private void showPre() {
        viewFlipper.showPrevious();
        int current=viewFlipper.getDisplayedChild();
        if(current==NUM-1){
            mDotList.get(0).setBackgroundResource(R.drawable.dot_normal);
        }else {
            mDotList.get(current+1).setBackgroundResource(R.drawable.dot_normal);
        }
        mDotList.get(current).setBackgroundResource(R.drawable.dot_focus);
    }

    public mainFragment( ) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.mainfragment,container,false);
        pager=(ViewPager)v.findViewById(R.id.viewpager);
        viewFlipper=(ViewFlipper)v.findViewById(R.id.viewflipper);
        SlideImage =new ImageView[5];
        SlideImage[0]=(ImageView)v.findViewById(R.id.slideimg1);
        SlideImage[1]=(ImageView)v.findViewById(R.id.slideimg2);
        SlideImage[2]=(ImageView)v.findViewById(R.id.slideimg3);
        SlideImage[3]=(ImageView)v.findViewById(R.id.slideimg4);
        SlideImage[4]=(ImageView)v.findViewById(R.id.slideimg5);

        SlideText =new TextView[5];
        SlideText[0]=(TextView)v.findViewById(R.id.slidetext1);
        SlideText[1]=(TextView)v.findViewById(R.id.slidetext2);
        SlideText[2]=(TextView)v.findViewById(R.id.slidetext3);
        SlideText[3]=(TextView)v.findViewById(R.id.slidetext4);
        SlideText[4]=(TextView)v.findViewById(R.id.slidetext5);

        mDotList =new ArrayList<View>();
        mDotList.add(v.findViewById(R.id.dot1));
        mDotList.add(v.findViewById(R.id.dot2));
        mDotList.add(v.findViewById(R.id.dot3));
        mDotList.add(v.findViewById(R.id.dot4));
        mDotList.add(v.findViewById(R.id.dot5));
        //滑动新闻滑动事件

        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x=event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        y=event.getX();
                        if(y>x){
                            mHandler.sendEmptyMessage(PREVIOUS);

                        }else if(y==x){
                            int index=viewFlipper.getDisplayedChild();
                            Intent intent=new Intent();
                            intent.setClass(getActivity(),articleDetail.class);

                            String linkUrl= SlideArticle.get(index).linkUrl;
                            intent.putExtra("Position",linkUrl);
                            startActivity(intent);
                        }else{
                            mHandler.sendEmptyMessage(NEXT);

                        }

                }
                return true;
            }
        });
        //点击滑动新闻事件


        sendMsg();

        asyncTask=new SlideasyncTask();
        asyncTask.execute("https://www.freebuf.com/");


        tabStrip=(PagerTabStrip)v.findViewById(R.id.pagestrip);
        tabStrip.setDrawFullUnderline(false);
        tabStrip.setTextSpacing(200);

        new_fragment new_fra=new new_fragment("https://www.freebuf.com/");
        point_fragment poi_fra=new point_fragment("https://www.freebuf.com/articles/neopoints/");
        deptih_fragment dep_fra=new deptih_fragment("https://www.freebuf.com/news/special/");
        report_fragment rep_fra=new report_fragment("https://www.freebuf.com/paper/");
        video_fragment vid_fra=new video_fragment("https://www.freebuf.com/video/");
        activity_fragment act_fra=new activity_fragment("https://www.freebuf.com/fevents/");
        manage_fragment man_fra=new manage_fragment("https://www.freebuf.com/articles/security-management/");
        viewcontainer.add(new_fra);
        viewcontainer.add(poi_fra);
        viewcontainer.add(dep_fra);
        viewcontainer.add(rep_fra);
        viewcontainer.add(man_fra);
        viewcontainer.add(vid_fra);
        viewcontainer.add(act_fra);



        titlescontainer.add("最新");
        titlescontainer.add("观点");
        titlescontainer.add("深度");
        titlescontainer.add("报告");
        titlescontainer.add("管理");
        titlescontainer.add("视频");
        titlescontainer.add("活动");


        pager.setAdapter(new myFragmentAdapter(getChildFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

  /*
        recycle=(RecyclerView)view_new.findViewById(R.id.new_article);
        mManager=new LinearLayoutManager(getContext());
        recycle.setLayoutManager(mManager);
        recycle.setItemAnimator(new DefaultItemAnimator());
        asyncTask=new articleAsyncTask();
        asyncTask.execute("http://www.freebuf.com/");
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
                    String base="http://www.freebuf.com/page/";
                    String path=base+MorePage+"";
                    MorePage++;
                    moreArticles.execute(path);

                    Log.e("articles.size()",articles.size()+"");
                    Log.e("MorePage",MorePage+"");

                }
            }
        });
        asyncTask=new articleAsyncTask();
        asyncTask.execute("http://www.freebuf.com/");
        */
        return v;
    }


    public class myFragmentAdapter extends FragmentStatePagerAdapter{

    public myFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return viewcontainer.get(position);
    }

    @Override
    public int getCount() {
        return viewcontainer.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlescontainer.get(position);
    }
}

public class SlideasyncTask extends AsyncTask<String,Integer,ArrayList<SlideArticle>> {


    protected ArrayList<SlideArticle> doInBackground(String... params) {
         ArticleService articleService=new ArticleService();
        SlideArticle=articleService.getSlideArticles(params[0]);
        return SlideArticle;
    }


    protected void onPostExecute( ArrayList<SlideArticle> Slidearticles) {
        ImageLoader imageLoader=ImageLoader.getInstance();

        for( int i=0;i<=4;i++) {
            imageLoader.displayImage(Slidearticles.get(i).imageUrl,SlideImage[i]);
            SlideText[i].setText(Slidearticles.get(i).title);

        }



    }
}



}
