package com.yf.re.freebuf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by lenovo on 2017/4/5.
 */

public class httpRequestService {

    public String phpSession,oAuth;
    public String wordpressCookie_one,wordpressCookie_two;
    public String Redirect_url,location_with_code;


    public void getloginAddr( final String url) {
        OkHttpClient okhttpClient = new OkHttpClient().newBuilder().followSslRedirects(false).followRedirects(false).build();
        Request request= new Request.Builder().url(url).get().build();



        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String redirect_url=response.header("Location");
                Log.e("Location",redirect_url+" ");
                Redirect_url=redirect_url;

            }
        });
    }

    public  void getoAuth(String str, final String name, String pass, String checkCode, String Cookie){
        final OkHttpClient okhttpClient = new OkHttpClient().newBuilder().followSslRedirects(false).followRedirects(false).build();

        FormBody.Builder body=new FormBody.Builder();
        body.add("username",name).add("password",pass).add("code",checkCode);
        Request request= new Request.Builder().url(str).post(body.build()).header("Cookie",Cookie).build();


        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                oAuth=response.header("Set-Cookie").split(";")[0];
                Log.e("oAuth:",oAuth+" ");
                Log.e("Response:",response.body().string());
                Log.e("Response:",response.headers().toString());
                Log.e("Response:",response.code()+"");
               getCheckCode(Redirect_url,name,phpSession,oAuth);
            }
        });
    }

public void getWordPressCookies(final String url,final  String name, final String phpCookie, final String oAuthCookie) {

    OkHttpClient okhttpClient = new OkHttpClient().newBuilder().followSslRedirects(false).followRedirects(false).build();
    Request request = new Request.Builder().url(url).get().header("Cookie", phpCookie).header("Cookie", oAuthCookie).build();


    okhttpClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.e("Response",response.headers().toString()+" ");
            getCheckCode(url,name,phpCookie,oAuthCookie);
        }
    });

}

    public void getCheckCode(String url,String name,String phpCookie,String oAuthCookie){
        OkHttpClient okhttpClient = new OkHttpClient().newBuilder().build();

        FormBody.Builder body=new FormBody.Builder();
        body.add("Token",name);
        Request request= new Request.Builder().url(url).post(body.build()).header("Cookie",phpCookie+";"+oAuthCookie).build();


        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                location_with_code=response.header("Location");
                Log.e("Response",response.headers().toString()+" ");
                Log.e("Location",location_with_code+" ");
            }
        });
    }






}
