package com.silas.asteroids.common.entity;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Character implements Entity
{
    protected double x;
    protected double y;
    protected final int width;
    protected final int height;
    protected final double scale;
    protected long lastFire = System.currentTimeMillis();
    protected int fireRate; // shots pr second
    protected double bulletSpeed;
    protected double speed = 2;
    protected double angle = 0;
    protected int hp;

    public Character(int width, int height, double scale, double x, double y, int hp, int fireRate, int bulletSpeed)
    {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.fireRate = fireRate;
        this.lastFire = fireRate;
        this.bulletSpeed = bulletSpeed;
    }

    public abstract void update(World world, GameData gameData);

    protected abstract void fire(World world);

    protected abstract Image getImg();

    protected abstract void move(GameData gameData);

    protected double calcAngle(double x, double y) {
        return Math.atan2(y - this.y, x - this.x);
    }

    public abstract void draw(GraphicsContext gc, GameData gameData);
}
