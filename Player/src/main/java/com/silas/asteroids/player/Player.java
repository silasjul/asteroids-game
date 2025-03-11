package com.silas.asteroids.player;

import com.silas.asteroids.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Set;


public class Player
{
    private double x;
    private double y;
    private final int width = 48;
    private final int height = 48;
    private double scale = 2.5;
    private double speed = 2;
    private double angle = 0;
    private int hp = 100;

    Sprite[] sprites = new Sprite[] {
            new Sprite("/player/pl100.png", width, height, scale),
            new Sprite("/player/pl75.png", width, height, scale),
            new Sprite("/player/pl50.png", width, height, scale),
            new Sprite("/player/pl25.png", width, height, scale),
    };

    public Player(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public void update(double mouseX, double mouseY, Set<String> keyMap) {
        calcAngle(mouseX, mouseY);
        move(keyMap);
    }

    public Image getImg() {
        if (hp < 25) return sprites[3].getImage(0);
        if (hp < 50) return sprites[2].getImage(0);
        if (hp < 75) return sprites[1].getImage(0);
        return sprites[0].getImage(0, -this.angle+Math.PI); // "-" rotates correct way. pi flips image
    }

    public void move(Set<String> keyMap)
    {
        // x-axis
        if (keyMap.contains("A")) x -= speed;
        if (keyMap.contains("D")) x += speed;

        // y-axis
        if (keyMap.contains("W")) y -= speed;
        if (keyMap.contains("S")) y += speed;
    }

    public void calcAngle(double x, double y) {
        this.angle = Math.atan2(x - this.x, y - this.y);
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(getImg(), x-width*scale/2, y-height*scale/2, width*scale, height*scale);
    }
}
