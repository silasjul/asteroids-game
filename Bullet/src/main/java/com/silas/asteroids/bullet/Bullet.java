package com.silas.asteroids.bullet;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import com.silas.asteroids.common.entity.Entity;
import com.silas.asteroids.common.entity.EntityType;
import com.silas.asteroids.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet extends Entity
{
    double speed;
    double angleRad;
    Sprite sprite;

    public enum Type {
        ZAP,
        BULLET,
        ROCKET,
        BALL
    }

    public Bullet(double x, double y, double speed, double angleRad, Type type, EntityType entityType) {
        super(x, y, 32,32, 20, 20, entityType);
        this.speed = speed;
        this.angleRad = angleRad;

        // Different bullets
        double imgRotation = angleRad + Math.PI/2;
        switch (type) {
            case ZAP -> this.sprite = new Sprite ("/player/projectiles/zap.png", width, height, 1, imgRotation);
            case BULLET -> this.sprite = new Sprite ("/player/projectiles/bullet.png", width, height, 1.5, imgRotation);
            case ROCKET -> this.sprite = new Sprite ("/player/projectiles/rocket.png", width, height, 1, imgRotation);
            case BALL -> new Sprite ("/player/projectiles/ball.png", width, height, 1, imgRotation);
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
        if (gameData.isAnimationFrame()) sprite.next();

        if (gameData.isTesting()) drawCenterCollider(gc);
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
        int max = 50; // Max distance allowed for a bullet to be out of canvas
        return this.x < -max || this.x > screenWidth + max || this.y < -max || this.y > screenHeight + max;
    }
}
