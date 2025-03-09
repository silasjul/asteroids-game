package com.silas.asteroids.player;

import com.silas.asteroids.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Set;


public class Player
{
    double x;
    double y;
    int width = 48, height = 48;
    double scale = 2.5;
    double speed = 2;

    int hp = 100;

    Sprite[] sprites = new Sprite[] {
            new Sprite("/player/pl100.png", width, height),
            new Sprite("/player/pl75.png", width, height),
            new Sprite("/player/pl50.png", width, height),
            new Sprite("/player/pl25.png", width, height),
    };

    public Player(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Image getImg() {
        if (hp < 25) return sprites[3].getImage(0);
        if (hp < 50) return sprites[2].getImage(0);
        if (hp < 75) return sprites[1].getImage(0);
        return sprites[0].getImage(0);
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

    public void draw(GraphicsContext gc) {
        gc.drawImage(getImg(), x, y, width*scale, height*scale);
    }
}
