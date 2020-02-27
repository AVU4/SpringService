package com.example.demo.service.impl;

import com.example.demo.entity.Point;
import com.example.demo.repository.PointRepository;
import com.example.demo.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PointServiceImpl implements PointService {
    @Autowired
    private PointRepository pointRepository;

    @Override
    public Point addPoint(Point point) {
        return pointRepository.saveAndFlush(point);
    }

    @Override
    public ArrayList<Point> load(String string) {
        return pointRepository.findAllByUsername(string);
    }
}
