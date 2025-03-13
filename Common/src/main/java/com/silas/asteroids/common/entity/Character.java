package com.silas.asteroids.common.entity;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Character implements Entity
{
    protected double x;
    protected double y;
    protected final int width;
    protected final int height;
    protected final double scale;
    protected long lastFire;
    protected int fireRate; // shots pr second
    protected double bulletSpeed;
    protected double speed;
    protected double angle = 0;
    protected int hp;
    protected int colliderWidth;
    protected int colliderHeight;

    public Character(int width, int height, double scale, double x, double y, int colliderWidth, int colliderHeight, int hp, double speed, int fireRate, int bulletSpeed)
    {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.speed = speed;
        this.fireRate = fireRate;
        this.lastFire = fireRate;
        this.bulletSpeed = bulletSpeed;
        this.colliderHeight = colliderHeight;
        this.colliderWidth = colliderWidth;
    }

    public abstract void update(World world, GameData gameData);

    protected abstract Image getImg();

    protected abstract void move(GameData gameData);

    protected abstract void fire(World world, GameData gameData);

    public abstract void draw(GraphicsContext gc, GameData gameData);

    protected double calcAngle(double x, double y) {
        return Math.atan2(y - this.y, x - this.x);
    }

    protected double getFireDelay() {return 1000. / this.fireRate;}

    public void drawCenterCollider(GraphicsContext gc) {
        gc.setFill(Color.web("aqua", 0.5));
        gc.fillRect(this.x-colliderWidth/2., this.y-colliderHeight/2., colliderWidth, colliderHeight);

        gc.setFill(Color.RED);
        int size = 5;
        gc.fillOval(this.x-size/2., this.y-size/2., size, size);
    }

    public double getCenterX() {return this.x;}
    public double getCenterY() {return this.y;}
}
