package com.yf.re.freebuf;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private TextView firstPage;
    private TextView more;
    private  TextView aboutMe;
    private TextView subRead;
    private FrameLayout ly_content;
    private mainFragment mf1;
    private AboutFragment mf4;
    private SubReadFragment  mf2;
    private  MoreFragment mf3;
    private FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    public void init() {
        firstPage=(TextView)findViewById(R.id.firstpage);
        aboutMe=(TextView)findViewById(R.id.AboutMe);
        subRead=(TextView)findViewById(R.id.subread);
        ly_content=(FrameLayout)findViewById(R.id.content_main);

        firstPage.setOnClickListener(this);
        subRead.setOnClickListener(this);
        aboutMe.setOnClickListener(this);
        selected();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        mf1=new mainFragment();
        transaction.replace(R.id.content_main,mf1);
        transaction.commit();
    }
public void hideFragments(FragmentTransaction transaction){
    if(mf1!=null){
        transaction.hide(mf1);
    }
    if(mf2!=null){
        transaction.hide(mf2);
    }

    if(mf4!=null){
        transaction.hide(mf4);
    }
}

    public void selected(){
        firstPage.setSelected(false);
        subRead.setSelected(false);
        aboutMe.setSelected(false);
    }

    @Override
    public void onClick(View v) {
   FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (v.getId()){
            case R.id.firstpage:
                selected();
                firstPage.setSelected(true);
                if(mf1==null){
                    mf1=new mainFragment();
                    transaction.add(R.id.content_main,mf1);
                }else{
                    transaction.show(mf1);
                }

                break;
            case R.id.subread:
                selected();
                subRead.setSelected(true);
                if(mf2==null){
                    mf2=new SubReadFragment();
                    transaction.add(R.id.content_main,mf2);
                }else{
                    transaction.show(mf2);
                }
                break;

            case R.id.AboutMe:
                selected();
                aboutMe.setSelected(true);
                if(mf4==null){
                    mf4=new AboutFragment();
                    transaction.add(R.id.content_main,mf4);
                }else{
                    transaction.show(mf4);
                }
                break;
        }
        transaction.commit();
    }
}
