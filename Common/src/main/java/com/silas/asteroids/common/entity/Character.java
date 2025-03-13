package com.silas.asteroids.common.entity;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Character extends Entity
{
    protected final double scale;
    protected long lastFire;
    protected int fireRate; // shots pr second
    protected double bulletSpeed;
    protected double speed;
    protected double angle = 0;
    protected int hp;
    protected int dmg;
    protected boolean isDead = false;

    public Character(double x, double y, int width, int height, int colliderWidth, int colliderHeight, EntityType type, double scale, int hp, int dmg, double speed, int fireRate, int bulletSpeed)
    {
        super(x, y, width, height, colliderWidth, colliderHeight, type);
        this.scale = scale;
        this.hp = hp;
        this.dmg = dmg;
        this.speed = speed;
        this.fireRate = fireRate;
        this.lastFire = fireRate;
        this.bulletSpeed = bulletSpeed;
    }

    public abstract void update(World world, GameData gameData);

    protected abstract Image getImg();

    protected abstract void move(GameData gameData, World world);

    protected abstract void fire(World world, GameData gameData);

    public abstract void draw(GraphicsContext gc, GameData gameData);

    protected double calcAngle(double x, double y) {
        return Math.atan2(y - this.y, x - this.x);
    }

    protected double getFireDelay() {return 1000. / this.fireRate;}

    public boolean isLoaded() {return System.currentTimeMillis() - lastFire > getFireDelay();}

    public void takeDmg(int dmg) {
        this.hp -= dmg;
        if (hp <= 0) die();
    }

    public abstract void die();

    public double getCenterX() {
        return this.x;}

    public double getCenterY() {
        return this.y;}

    public int getDmg()  {
        return this.dmg;
    }
}
