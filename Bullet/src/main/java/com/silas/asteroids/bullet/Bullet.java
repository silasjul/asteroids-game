package com.silas.asteroids.bullet;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.entity.Entity;
import com.silas.asteroids.common.data.World;
import com.silas.asteroids.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet implements Entity
{
    double x;
    double y;
    double speed;
    double angleRad;
    Sprite sprite;

    public enum Type {
        ZAP,
        BULLET,
        ROCKET,
        BALL
    }

    public Bullet(double x, double y, double speed, double angleRad, Type type) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angleRad = angleRad;

        // Different bullets
        switch (type) {
            case ZAP -> this.sprite = new Sprite ("/player/projectiles/zap.png", 32, 32, 1, this.angleRad);
            case BULLET -> this.sprite = new Sprite ("/player/projectiles/bullet.png", 32, 32, 1.5, this.angleRad);
            case ROCKET -> this.sprite = new Sprite ("/player/projectiles/rocket.png", 32, 32, 1, this.angleRad);
            case BALL -> new Sprite ("/player/projectiles/ball.png", 32, 32, 1, this.angleRad);
        }
    }

    public void move() {
        this.x = this.x + speed * Math.cos(angleRad);
        this.y = this.y + speed * Math.sin(angleRad);
    }

    @Override
    public void draw(GraphicsContext gc, GameData gameData) {
        Image img = sprite.getCurrentImage();
        gc.drawImage(img, x - img.getWidth()/2, y - img.getHeight()/2);
        if (gameData.getFrame() % 14 == 0) sprite.next();
    }

    @Override
    public void update(World world, GameData gameData) {
        if (isOutOfScreen(world.getWidth(), world.getHeight())) {
            world.removeEntity(this);
            return;
        }
        move();
    }

    public boolean isOutOfScreen(int screenWidth, int screenHeight) {
        return this.x < 0 || this.x > screenWidth || this.y < 0 || this.y > screenHeight;
    }
}
