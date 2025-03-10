package com.silas.asteroids.bullet;


import com.silas.asteroids.entity.Entity;

public class Bullet extends Entity
{
    double speed;
    double angle;

    public Bullet(double x, double y, double speed, double angle) {
        super(x, y);
        this.speed = speed;
        this.angle = angle;
    }

    public void move() {

    }
}
