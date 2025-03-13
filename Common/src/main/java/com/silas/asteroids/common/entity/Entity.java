package com.silas.asteroids.common.entity;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Entity {
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected int colliderWidth;
    protected int colliderHeight;
    protected EntityType type;

    public Entity(double x, double y, int width, int height, int colliderWidth, int colliderHeight, EntityType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colliderWidth = colliderWidth;
        this.colliderHeight = colliderHeight;
        this.type = type;
    }


    public abstract void draw(GraphicsContext gc, GameData gameData);
    public abstract void update(World world, GameData gameData);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getColliderWidth() {
        return colliderWidth;
    }

    public int getColliderHeight() {
        return colliderHeight;
    }

    public EntityType getType() {
        return type;
    }

    public void drawCenterCollider(GraphicsContext gc) {
        gc.setFill(Color.web("aqua", 0.5));
        gc.fillRect(this.x-colliderWidth/2., this.y-colliderHeight/2., colliderWidth, colliderHeight);

        gc.setFill(Color.RED);
        int size = 5;
        gc.fillOval(this.x-size/2., this.y-size/2., size, size);
    }
}
