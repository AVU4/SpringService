package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "points")
public class Point {

    @Id
    @JsonIgnore
    @GeneratedValue
    private Long id;

    @JsonProperty(value = "X")
    @Column
    private String x;
    @JsonProperty(value = "Y")
    @Column
    private String y;
    @JsonProperty(value = "R")
    @Column
    private String r;
    @JsonProperty(value = "hit")
    @Column
    private String result;
    @JsonIgnore
    @Column
    private String username;

    public Point(String x, String y, String r, String result, String username) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Point(){
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }
}
