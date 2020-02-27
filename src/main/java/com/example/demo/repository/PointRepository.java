package com.example.demo.repository;

import com.example.demo.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface PointRepository extends JpaRepository<Point, Long> {
    ArrayList<Point> findAllByUsername(String username);
}
