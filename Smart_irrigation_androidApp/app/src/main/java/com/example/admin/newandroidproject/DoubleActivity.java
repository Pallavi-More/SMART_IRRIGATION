package com.example.admin.newandroidproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DoubleActivity extends AppCompatActivity  {
    EditText uname,uemail,ucontact,uaddress,ustate,username,password,cpassword;
    Button registerbtn;
    TextView status;
    String doubledValue =null;
    Context c;
    private static final int REGISTRATION_TIMEOUT = 3 * 1000;
    private static final int WAIT_TIMEOUT = 30 * 1000;
    private final HttpClient httpclient = new DefaultHttpClient();

    final HttpParams params = httpclient.getParams();
    HttpResponse response;
    private String content =  null;
    private boolean error = false;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double);
        setUi();
    }

    private void setUi() {
        uname = (EditText) findViewById(R.id.etName);
        uemail = (EditText) findViewById(R.id.etEmail);
        ucontact = (EditText) findViewById(R.id.etContact);
        uaddress = (EditText) findViewById(R.id.etAddress);
        ustate = (EditText) findViewById(R.id.etState);
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etpasswd);
        cpassword = (EditText) findViewById(R.id.etcpasswd);
        status = (TextView) findViewById(R.id.tvstatus);
        registerbtn = (Button) findViewById(R.id.btnregister);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnregister:
                        if (validation()) {
                            callAysnkTask();
                            Intent MyRegisterIntent = new Intent(DoubleActivity.this, MainActivity.class);
                            startActivity(MyRegisterIntent);
                        }

                        break;
                }

            }
        });
    }


        /**
         * Validation reister
         * @return
         */
    private boolean validation() {
        String conpassword=cpassword.getText().toString();
        String pass=password.getText().toString();
        if(uname.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(DoubleActivity.this,"Please enter name.",Toast.LENGTH_SHORT).show();
            return false;
        }else  if(ucontact.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(DoubleActivity.this,"Please enter contact no.",Toast.LENGTH_SHORT).show();
            return false;
        }else  if(uemail.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(DoubleActivity.this,"Please enter email.",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(uaddress.getText().toString().trim().equalsIgnoreCase(""))
        {
            Toast.makeText(DoubleActivity.this,"Please Enter Address",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(ustate.getText().toString().trim().equalsIgnoreCase(""))
        {
            Toast.makeText(DoubleActivity.this,"Please Enter State",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(username.getText().toString().trim().equalsIgnoreCase(""))
        {
            Toast.makeText(DoubleActivity.this,"Please Enter Username",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(password.getText().toString().trim().equalsIgnoreCase(""))
        {
            Toast.makeText(DoubleActivity.this,"Please Enter Password",Toast.LENGTH_LONG).show();
            return false;

        }
        else if(password.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(DoubleActivity.this,"Please Enter Password",Toast.LENGTH_LONG).show();
            return false;

        }
        else if(cpassword.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(DoubleActivity.this,"Please Enter Password",Toast.LENGTH_LONG).show();
            return false;

        }
        else if(!pass.equals(conpassword))
        {
            Toast.makeText(DoubleActivity.this,"Password Not Match",Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }

    private void callAysnkTask() {
        try {
            new MyAsyncTask().execute(uname.getText().toString().trim(),ucontact.getText().toString().trim(),uemail.getText().toString().trim(),uaddress.getText().toString().trim(),ustate.getText().toString().trim(),username.getText().toString().trim(),password.getText().toString().trim(),cpassword.getText().toString().trim());

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
            HttpPost httppost = new HttpPost("http://192.168.43.236:8080/ConnectivityServer/RegistrationServlet");
            String origresponseText="";
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Name",valueIWantToSend[0]));
                nameValuePairs.add(new BasicNameValuePair("ContactNo", valueIWantToSend[1]));
                nameValuePairs.add(new BasicNameValuePair("EmailId", valueIWantToSend[2]));
                nameValuePairs.add(new BasicNameValuePair("Address", valueIWantToSend[3]));
                nameValuePairs.add(new BasicNameValuePair("State", valueIWantToSend[4]));
                nameValuePairs.add(new BasicNameValuePair("Username", valueIWantToSend[5]));
                nameValuePairs.add(new BasicNameValuePair("Password", valueIWantToSend[6]));
                nameValuePairs.add(new BasicNameValuePair("ConfirmPassword", valueIWantToSend[7]));


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
        uname.setText("");
        ucontact.setText("");
        uemail.setText("");
        uaddress.setText("");
        ustate.setText("");
        username.setText("");
        password.setText("");
        cpassword.setText("");
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

