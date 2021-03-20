package com.example.admin.newandroidproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PotatoesActivity extends AppCompatActivity {
    String str1;
    Switch simpleSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potatoes);

        simpleSwitch = (Switch) findViewById(R.id.switchp);
        simpleSwitch.setTextOn("On");
        simpleSwitch.setTextOff("Off");

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // The toggle is enabled
                    str1 = simpleSwitch.getTextOn().toString();
                    callAysnkTask1();
                } else {
                    // The toggle is disabled
                    str1 = simpleSwitch.getTextOff().toString();
                    callAysnkTask();
                }
            }
        });


    }

    private void callAysnkTask1() {
        try {
            new PotatoesActivity.MyAsyncTask1().execute(str1.toString().trim());

            // clearPrevioudData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyAsyncTask1 extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String s = postData1(params);
            return s;
        }

        protected void onPostExecute(String result) {
            // pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Switch1 -  " + str1, Toast.LENGTH_SHORT).show();
        }

        protected void onProgressUpdate(Integer... progress) {
            // pb.setProgress(progress[0]);
        }

        public String postData1(String valueIWantToSend[]) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.236:8080/ConnectivityServer/EventServlet");
            String origresponseText = "";
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("On", valueIWantToSend[0]));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
         /* execute */
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity rp = response.getEntity();
                origresponseText = readContent1(response);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            String responseText = origresponseText.substring(7, origresponseText.length());
            return responseText;
        }


    }

    /**
     * Clear View Data after registration
     */


    String readContent1(HttpResponse response) {
        String text = "";
        InputStream in = null;

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
        } finally {
            try {

                in.close();
            } catch (Exception ex) {
            }
        }

        return text;
    }

    private void callAysnkTask() {
        try {
            new PotatoesActivity.MyAsyncTask().execute(str1.toString().trim());

            // clearPrevioudData();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String s=postData(params);
            return s;
        }

        protected void onPostExecute(String result){
            // pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Switch1 -  " + str1 , Toast.LENGTH_SHORT).show();
        }
        protected void onProgressUpdate(Integer... progress){
            // pb.setProgress(progress[0]);
        }

        public String postData(String valueIWantToSend[]) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.236:8080/ConnectivityServer/EventServlet");
            String origresponseText="";
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Off",valueIWantToSend[0]));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
