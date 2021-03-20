package com.example.admin.newandroidproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FarmDetailsActivity extends AppCompatActivity {
    EditText Farm_name,location;
    Button adddetailbtn,addMoreFarmbtn;
    private final HttpClient httpclient = new DefaultHttpClient();
    TextView status;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_details);
        setUi();
        //addMoreFarmbtn=(Button)findViewById(R.id.btnMoreFarm);

    }
    private void setUi() {
        Farm_name = (EditText) findViewById(R.id.etFarmName);
        location =(EditText) findViewById(R.id.etLocation);
        //status=(TextView)findViewById(R.id.tvstatus);
        adddetailbtn=(Button)findViewById(R.id.btnFarmDetail);
        adddetailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnFarmDetail:
                        if (validation()) {
                            callAysnkTask();
                            Intent MyFarmIntent=new Intent(FarmDetailsActivity.this,AddFieldActivity.class);
                            startActivity(MyFarmIntent);

                        }
                        break;
                }
            }
        });

        addMoreFarmbtn=(Button)findViewById(R.id.btnMoreFarm);
        addMoreFarmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnMoreFarm:
                        if (validation()) {
                            callAysnkTask();

                        }
                        break;
                }
            }
        });
    }

    /*
     * Validation reister
     * @return
     */
    private boolean validation() {

        if(Farm_name.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(FarmDetailsActivity.this,"Please enter name.",Toast.LENGTH_SHORT).show();
            return false;
        }else  if(location.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(FarmDetailsActivity.this,"Please enter contact no.",Toast.LENGTH_SHORT).show();
            return false;
        }

        return  true;
    }

    private void callAysnkTask() {
        try {
            new MyAsyncTask().execute(Farm_name.getText().toString().trim(),location.getText().toString().trim());

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
            HttpPost httppost = new HttpPost("http://192.168.43.236:8080/ConnectivityServer/FarmServlet");
            String origresponseText="";
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("FarmName",valueIWantToSend[0]));
                nameValuePairs.add(new BasicNameValuePair("Location", valueIWantToSend[1]));
                // nameValuePairs.add(new BasicNameValuePair("param3", valueIWantToSend[2]));
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
        Farm_name.setText("");
        location.setText("");

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
