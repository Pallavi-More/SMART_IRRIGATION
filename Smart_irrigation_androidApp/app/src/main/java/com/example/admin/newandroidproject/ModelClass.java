package com.example.admin.newandroidproject;

/**
 * Created by Admin on 5/6/2018.
 */

public class ModelClass {

        private float temp;
        private float humi;
        private float moisture;
        //private String name;
        //private String bloodgrp;
        //private String age;

        public float gettemp() {
            return temp;
        }

        public ModelClass(float temp, float humi, float moisture) {
            this.temp = temp;
            this.humi = humi;
            this.moisture = moisture;
            //this.name = name;
            //this.bloodgrp = bloodgrp;
            //this.age = age;
        }

        public void settemp(float age) {
            this.temp = temp;
        }

    /*public ModelClassDonateFood(String fooditem, String time, String date, String name, String bloodgrp) {
        this.fooditem = fooditem;
        this.time = time;
        this.date = date;
        this.name = name;
        this.bloodgrp = bloodgrp;
    }*/

        public void sethumi(float humi) {
            this.humi = humi;
        }

        public float gethumi() {
            return humi;
        }

    /*public ModelClassDonateFood(String fooditem, String time, String date, String name) {
        this.fooditem = fooditem;
        this.time = time;
        this.date = date;
        this.name = name;
    }*/

        public float getmoisture() {
            return moisture;
        }





    }
