package com.example.admin.newandroidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewFarmActivity extends AppCompatActivity {
Button cottonbtn,sugarcanebtn,wheatbtn,potatoesbtn,ricebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_farm);
        cottonbtn=(Button)findViewById(R.id.btncotton);
        sugarcanebtn=(Button)findViewById(R.id.btnsugar);
        wheatbtn=(Button)findViewById(R.id.btnwheat);
        potatoesbtn=(Button)findViewById(R.id.btnpotatoe);
        ricebtn=(Button)findViewById(R.id.btnrice);
       cottonbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Cottonintent =new Intent(ViewFarmActivity.this,CottonActivity.class);
                startActivity(Cottonintent);
            }

        });
        sugarcanebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Sugarintent =new Intent(ViewFarmActivity.this,SugarcaneActivity.class);
                startActivity(Sugarintent);
            }

        });
        wheatbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Wheatintent =new Intent(ViewFarmActivity.this,WheatActivity.class);
                startActivity(Wheatintent);
            }

        });
        potatoesbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Potatoesintent =new Intent(ViewFarmActivity.this,PotatoesActivity.class);
                startActivity(Potatoesintent);
            }

        });
        ricebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Riceintent =new Intent(ViewFarmActivity.this,RiceActivity.class);
                startActivity(Riceintent);
            }

        });
    }
}