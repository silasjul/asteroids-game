package com.silas.asteroids.player;

import com.silas.asteroids.bullet.Bullet;
import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import com.silas.asteroids.common.entity.Character;
import com.silas.asteroids.common.entity.EntityType;
import com.silas.asteroids.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;

public class Player extends Character {
    private int xp = 0;

    private enum Condition {
        PERFECT,
        GOOD,
        DAMAGED,
        SHIT
    }

    HashMap<Condition, String> shipMap = new HashMap<>();

    public Player(double screenWidth, double screenHeight)
    {
        super(0,0, 48, 48, 40,40, EntityType.PLAYER, 2.5, 100,10, 2,5, 5);

        // Spawn position
        this.x = screenWidth / 2. - this.width / 2.;
        this.y = screenHeight / 1.8;


        // Ships
        shipMap.put(Condition.PERFECT, "/player/pl100.png");
        shipMap.put(Condition.GOOD, "/player/pl75.png");
        shipMap.put(Condition.DAMAGED, "/player/pl50.png");
        shipMap.put(Condition.SHIT, "/player/pl25.png");
    }

    @Override
    public void update(World world, GameData gameData) {
        this.angle = calcAngle(gameData.getMousePosX(), gameData.getMousePosY());
        move(gameData);

        // Bullet logic
        if (gameData.getMousePressed() && isLoaded()) {
            fire(world, gameData);
        }
    }

    @Override
    protected void fire(World world, GameData gameData) {
        Bullet bullet = new Bullet(this.x, this.y, this.bulletSpeed, this.angle, Bullet.Type.BULLET, EntityType.PLAYERBULLET);
        world.addEntity(bullet);
        lastFire = System.currentTimeMillis();
    }

    @Override
    public Image getImg() {
        Condition c;
        if (hp >= 75) c = Condition.PERFECT;
        else if (hp >= 50) c = Condition.GOOD;
        else if (hp >= 25) c = Condition.DAMAGED;
        else c = Condition.SHIT;

        Sprite sprite = new Sprite (this.shipMap.get(c), this.width, this.height, this.scale, this.angle + Math.PI/2);
        return sprite.getImage(0);
    }

    @Override
    public void move(GameData gameData)
    {
        // x-axis
        if (gameData.isKeyPressed("A")) x -= speed;
        if (gameData.isKeyPressed("D")) x += speed;

        // y-axis
        if (gameData.isKeyPressed("W")) y -= speed;
        if (gameData.isKeyPressed("S")) y += speed;
    }

    @Override
    public void draw(GraphicsContext gc, GameData gameData) {
        gc.drawImage(getImg(), x-this.width*scale/2, y-this.height*scale/2, width*scale, height*scale);

        if (gameData.isTesting()) drawCenterCollider(gc);
    }

    @Override
    public void die() {

    }
}
