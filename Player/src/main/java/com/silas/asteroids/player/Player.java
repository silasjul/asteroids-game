package com.silas.asteroids.player;

import com.silas.asteroids.bullet.Bullet;
import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import com.silas.asteroids.common.entity.Character;
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

    public Player(double sceenWidth, double sceenHeight)
    {
        super(48, 48, 2.5, 0, 0, 100, 10, 15);

        // Spawn position
        this.x = sceenWidth / 2. - this.width / 2.;
        this.y = sceenHeight / 1.8;


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
        double fireDelay = 1000. / this.fireRate;
        if (gameData.getMousePressed() && System.currentTimeMillis() - lastFire > fireDelay) {
            fire(world);
            lastFire = System.currentTimeMillis();
        }
    }

    @Override
    protected void fire(World world) {
        Bullet bullet = new Bullet(this.x, this.y, this.bulletSpeed, this.angle, Bullet.Type.ZAP);
        world.addEntity(bullet);
    }

    @Override
    public Image getImg() {
        Condition c;
        if (hp >= 75) c = Condition.PERFECT;
        else if (hp >= 50) c = Condition.GOOD;
        else if (hp >= 25) c = Condition.DAMAGED;
        else c = Condition.SHIT;

        Sprite sprite = new Sprite (this.shipMap.get(c), this.width, this.height, this.scale, this.angle);
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
    }
}
