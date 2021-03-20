package com.example.admin.newandroidproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.List;

public class AddFieldActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button saveFieldDetailbtn, addMoreFieldbtn;
    EditText crop_name, DeviceCode;
    Spinner CropNameSpinner;
    Spinner Soilspinner;
    private final HttpClient httpclient = new DefaultHttpClient();
    TextView status;
    Context c;
    private TextView mDisplayDate;
    private static final String TAG = "AddFieldActivity";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_field);
        setUi();
        //addMoreFarmbtn=(Button)findViewById(R.id.btnMoreFarm);

    }

    private void setUi() {
        //crop_name = (EditText) findViewById(R.id.etCropName);

        DeviceCode = (EditText) findViewById(R.id.etDeviceCode);
        //SoilSpinner = (Spinner) findViewById(R.id.SoilTypespinner);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddFieldActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: yyyy/mm/dd: " + year + "/" + month + "/" + day);

                String date = year + "/" + month + "/" + day;
                mDisplayDate.setText(date);
            }
        };


        addMoreFieldbtn = (Button) findViewById(R.id.btnAddmorefield);
        addMoreFieldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnAddmorefield:
                        if (validation()) {
                            callAysnkTask();
                            Intent MyMoreFieldIntent = new Intent(AddFieldActivity.this, AddFieldActivity.class);
                            startActivity(MyMoreFieldIntent);
                        }

                        break;
                }

            }
        });
        saveFieldDetailbtn = (Button) findViewById(R.id.btnSaveField);
        saveFieldDetailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnSaveField:
                        if (validation()) {
                            callAysnkTask();
                            Intent SaveFieldIntent = new Intent(AddFieldActivity.this, HomeActivity.class);
                            startActivity(SaveFieldIntent);
                        }

                        break;
                }

            }
        });
        // Spinner element
        Soilspinner = (Spinner) findViewById(R.id.SoilTypespinner);

        // Spinner click listener
        Soilspinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Black");
        categories.add("Red");
        categories.add("Normal");
       // categories.add("Slity");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        Soilspinner.setAdapter(dataAdapter);


        CropNameSpinner = (Spinner) findViewById(R.id.cropSpinner);

        // Spinner click listener
        CropNameSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Soyabeans");
        categories1.add("SugarCane");
        categories1.add("Wheat");
        categories1.add("Tomatoes");
        categories1.add("Rice");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        CropNameSpinner.setAdapter(dataAdapter1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private boolean validation() {
        if(DeviceCode.getText().toString().trim().equalsIgnoreCase("")){
            Toast.makeText(AddFieldActivity.this,"Please enter contact no.",Toast.LENGTH_SHORT).show();
            return false;
        }
else if(mDisplayDate.getText().toString().trim().equalsIgnoreCase("Select date"))
        {
            Toast.makeText(AddFieldActivity.this,"Please select planted date",Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }

    private void callAysnkTask() {
        try {
            new AddFieldActivity.MyAsyncTask().execute(CropNameSpinner.getSelectedItem().toString().trim(),Soilspinner.getSelectedItem().toString().trim(),mDisplayDate.getText().toString().trim(),DeviceCode.getText().toString().trim());

            clearPrevioudData();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
   /* public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String sSelect =parent.getItemAtPosition(pos).toString();
        Toast.makeText(this,sSelect, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback


    }
*/

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
            HttpPost httppost = new HttpPost("http://192.168.43.236:8080/ConnectivityServer/AddFieldServlet");
            String origresponseText="";
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("CropName",valueIWantToSend[0]));
                nameValuePairs.add(new BasicNameValuePair("SoilType", valueIWantToSend[1]));
                nameValuePairs.add(new BasicNameValuePair("Date", valueIWantToSend[2]));
                nameValuePairs.add(new BasicNameValuePair("DeviceCode", valueIWantToSend[3]));
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

        DeviceCode.setText("");

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

