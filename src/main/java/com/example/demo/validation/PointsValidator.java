package com.example.demo.validation;

import com.example.demo.entity.Point;
import org.springframework.stereotype.Component;

@Component
public class PointsValidator{

    public boolean validate(Object o) {
        Point point = (Point) o;
        try{
            Double x = Double.parseDouble(point.getX());
        }catch (NumberFormatException | NullPointerException e){
            return false;
        }
        try {
            Double y = Double.parseDouble(point.getY());
            if (!((y > -3) && (y < 3))){
                return false;
            }
        }catch (NumberFormatException | NullPointerException e){
            return false;
        }
        try{
            Double R = Double.parseDouble(point.getR());
            if ((R != 1) && (R != 2) && (R != 3) && (R != 4) && (R != 5)){
                throw new NullPointerException();
            }
        }catch (NumberFormatException | NullPointerException e){
            return false;
        }
        return true;
    }
}

