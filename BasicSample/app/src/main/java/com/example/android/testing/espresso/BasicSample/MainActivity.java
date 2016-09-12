package com.example.android.testing.espresso.BasicSample;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    //模拟器自己把自己当成localhost了，服务器应该为10.0.2.2
    private static String url = "http://192.168.191.1:8888/Struts2Demo/login4app.action?username=123456";
    private TextView mTextView;
    HttpClient client;
    HttpPost request;
    HttpResponse response;
    String res;
    Handler handler = new Handler()
    {
        public void handleMessage(Message msg){
            if(msg.what == 0x123){
                mTextView.append(msg.obj.toString() + "\n");
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTextView = (TextView) this.findViewById(R.id.textView);
        client = new DefaultHttpClient();
        getData();

    }

    public void getData(){
        mTextView.setText("hehe");
        new Thread(){
            @Override
            public void run(){
                try {
                    request = new HttpPost(new URI(url));
                    response = client.execute(request);
                    if(response.getStatusLine().getStatusCode() == 200){
                        HttpEntity entity = response.getEntity();
                        if(entity != null){
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(entity.getContent()));
                            String line = null;
                            while ((line = br.readLine()) != null){
                                Message msg = new Message();
                                msg.what=0x123;
                                msg.obj = line;
                                handler.sendMessage(msg);
                            }
//
//                            String out = EntityUtils.toString(entity);
//                            Message msg1 = new Message();
//                            msg1.what=0x123;
//                            msg1.obj = out;
//                            handler.sendMessage(msg1);
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }.start();

    }


}



    /*public void accessSecret(View v){
        mEditText.setText("");
        new Thread(){
            @Override
            public void run(){

                try {
                    request = new HttpPost(new URI(url));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                HttpResponse response= null;
                try {
                    response = client.execute(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //判断请求是否成功
                if(response.getStatusLine().getStatusCode()==200){
                    HttpEntity entity=response.getEntity();
                    if(entity!=null){
                        String out= null;
                        try {
                            out = EntityUtils.toString(entity);
                            Message msg = new Message();
                            msg.what = 0x123;
                            msg.obj = out;
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //new AlertDialog.Builder(this).setMessage(out).create().show();
                    }
                }

            }
        }.start();
    }
*/


    /**
     * 请求服务
     * @param url
     */
   /* private void getPDAServerData(String url){
        url+="?username=123456";
        HttpClient client=new DefaultHttpClient();
        HttpPost request;
        try {
            request = new HttpPost(new URI(url));
            HttpResponse response=client.execute(request);
            //判断请求是否成功
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity entity=response.getEntity();
                if(entity!=null){
                    String out= EntityUtils.toString(entity);
                    new AlertDialog.Builder(this).setMessage(out).create().show();
                }
            }

        }catch (URISyntaxException e) {
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/