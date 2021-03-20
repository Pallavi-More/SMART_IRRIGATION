package com.example.admin.newandroidproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity   {
Button registerbtn,loginbtn;
EditText username,password;
    //JSONObject json;
    private final HttpClient httpclient = new DefaultHttpClient();
    final HttpParams params = httpclient.getParams();
    HttpResponse response;
    private String content =  null;
    private boolean error = false;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUi();
    }

    private void setUi() {

        username = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPassword);
       // loginbtn = (Button) findViewById(R.id.btnLogin);
        loginbtn=(Button)findViewById(R.id.btnLogin);
        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch (v.getId()){
                    case R.id.btnLogin:
                        if(validation()) {
                            callAysnkTask();

                               Intent myloginIntent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(myloginIntent);

                        }

                        break;
                }

            }
        });

        registerbtn=(Button)findViewById(R.id.btnSignup);
        registerbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(MainActivity.this,"Succesfully",Toast.LENGTH_LONG).show();
                Intent myRegisterIntent=new Intent(MainActivity.this,DoubleActivity.class);
                startActivity(myRegisterIntent);

            }
        });
    }
    /**
     * Validation reister
     * @return
     */
    private boolean validation() {
        if(username.getText().toString().trim().equalsIgnoreCase(""))
        {
            Toast.makeText(MainActivity.this,"Please Enter Username",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(password.getText().toString().trim().equalsIgnoreCase(""))
        {
            Toast.makeText(MainActivity.this,"Please Enter Password",Toast.LENGTH_LONG).show();
            return false;

        }
        return  true;
    }

    private void callAysnkTask() {
        try {
            new MainActivity.MyAsyncTask().execute(username.getText().toString().trim(),password.getText().toString().trim());

            clearPrevioudData();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Registration Asyanck task call
     */
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String s=postData(params);


            return s;
        }

        protected void onPostExecute(String result){
            // pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
        protected void onProgressUpdate(Integer... progress){
            // pb.setProgress(progress[0]);
        }

        public String postData(String valueIWantToSend[]) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.236:8080/ConnectivityServer/LoginServlet");
            String origresponseText="";
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Username",valueIWantToSend[0]));
                nameValuePairs.add(new BasicNameValuePair("Password", valueIWantToSend[1]));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
         /* execute */
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity rp = response.getEntity();
                origresponseText=readContent(response);

            }
            catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
            }
            String responseText = origresponseText.substring(7, origresponseText.length());
            return responseText;
        }


    }

    /**
     * Clear View Data after registration
     */
    private void clearPrevioudData() {

        username.setText("");
        password.setText("");
    }

    String readContent(HttpResponse response)
    {
        String text = "";
        InputStream in =null;

        try {
            in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            text = sb.toString();

        } catch (IllegalStateException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {

                in.close();
            } catch (Exception ex) {
            }
        }
        return text;
    }
}
