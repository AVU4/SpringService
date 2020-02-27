package com.example.demo.controller;


import com.example.demo.entity.Point;
import com.example.demo.service.PointService;
import com.example.demo.util.PointResult;
import com.example.demo.validation.PointsValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RestController
public class PointController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private PointResult pointResult;

    @Autowired
    private PointsValidator pointsValidator;

    @Autowired
    private PointService pointService;


    @CrossOrigin
    @PostMapping("/send")
    public String addPoint(HttpServletRequest request){
        try {
            Scanner scanner = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            Point point = mapper.readValue(scanner.next(), Point.class);
            if (pointsValidator.validate(point)){
                String result = pointResult.getResult(point.getX(),point.getY(), point.getR());
                Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
                String username = auth.getName();
                Point currentPoint = new Point(point.getX(), point.getY(), point.getR(), result, username);
                pointService.addPoint(currentPoint);
                Map<Object, Object> responce = new HashMap<>();
                responce.put("hit", result);
                responce.put("error", "false");
                return mapper.writeValueAsString(responce);
            }else{
                Map<Object, Object> responce = new HashMap<>();
                responce.put("hit", "false");
                responce.put("error", "true");
                return mapper.writeValueAsString(responce);
            }
        }catch (IOException e){
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/load")
    public String load(){
        try {
            Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            ArrayList<Point> points = pointService.load(username);
            return mapper.writeValueAsString(points);
        }catch (JsonProcessingException e){
            return null;
        }
    }
}
