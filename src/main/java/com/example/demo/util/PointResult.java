package com.example.demo.util;


import org.springframework.stereotype.Component;

@Component
public class PointResult {

    public String getResult(String  X, String Y, String R){
        Double x = Double.parseDouble(X.replace(",","."));
        Double y = Double.parseDouble(Y.replace(",","."));
        Double r = Double.parseDouble(R.replace(",","."));

        if (((y >= 2*x- r) && (x >= 0 && y <= 0)) || ((x < 0 && y > 0) && (x*x+ y*y <= (r*r)/4)) || ((x > 0 && y > 0) && (x <= r && y <= r))){
            return "true";
        }else{
            return "false";
        }

    }
}
