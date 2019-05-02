package com.yf.re.freebuf;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.os.SystemClock.sleep;


/**
 * Created by lenovo on 2017/4/6.
 */

public class LoginDialog extends Activity {

    ImageButton login;
    ImageView check_code;
    EditText userName,userPass,CheckCode;
    httpRequestService service;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        userName=(EditText)findViewById(R.id.username_text);
        userPass=(EditText)findViewById(R.id.userpass_text);
        CheckCode=(EditText)findViewById(R.id.checkcode_text);
        check_code=(ImageView)findViewById(R.id.checkcode);

        service=new httpRequestService();
        service.getloginAddr("https://www.freebuf.com/oauth");

        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getCheckCode("https://account.tophant.com/captcha");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        check_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCheckCode("https://account.tophant.com/captcha");
            }
        });


        login=(ImageButton)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=userName.getText().toString();
                String pass=userPass.getText().toString();
                String checkcode= CheckCode.getText().toString();
                service.getoAuth(service.Redirect_url,name,pass,checkcode,service.phpSession);

            }
        });

    }
    public void getCheckCode(String url) throws IOException {
        OkHttpClient okhttpClient = new OkHttpClient().newBuilder().followSslRedirects(false).followRedirects(false).build();
        Request request= new Request.Builder().url(url).get().build();
       try {
           Response response=okhttpClient.newCall(request).execute();
           InputStream in=response.body().byteStream();

           byte byte_img[]=response.body().bytes();
           Message message=handler.obtainMessage();
           message.what=1;

           message.obj=byte_img;
           handler.sendMessage(message);

           service.phpSession=response.header("Set-Cookie").split(";")[0];

           Log.e("phpsess",service.phpSession+" ");

       }catch (IOException e){
          e.printStackTrace();
       }
        /*
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in=response.body().byteStream();

                byte byte_img[]=response.body().bytes();
                Message message=handler.obtainMessage();
                message.what=1;

                message.obj=byte_img;
                handler.sendMessage(message);

                service.phpSession=response.header("Set-Cookie").split(";")[0];

                Log.e("phpsess",service.phpSession+" ");
            }
        });*/


    }
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    byte[] image=(byte[])msg.obj;
                    Bitmap bitmap=BitmapFactory.decodeByteArray(image,0,image.length);
                    check_code.setImageBitmap(bitmap);
                    break;
            }
        }
    };

}
