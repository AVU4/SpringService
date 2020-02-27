package com.example.demo.service;

import com.example.demo.entity.Point;

import java.util.ArrayList;

public interface PointService {

    Point addPoint(Point point);
    ArrayList<Point> load(String string);
}
