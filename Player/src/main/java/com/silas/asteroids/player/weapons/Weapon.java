package com.silas.asteroids.player.weapons;

import com.silas.asteroids.bullet.Bullet;
import com.silas.asteroids.sprite.Sprite;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Weapon {
    public enum FirePos {
        LEFT,
        MIDDLE,
        RIGHT
    }

    private Map<Integer, FirePos> frameToFire = new HashMap<>();
    private Bullet.Type bulletType;
    private int currentFrame = 0;
    private String weaponSprite;
    private double scale;
    private final int width = 48;
    private final int height = 48;

    public Weapon(Map<Integer, FirePos> frameToFire, String weaponSprite, double playerScale) {
        this.frameToFire = frameToFire;
        this.weaponSprite = weaponSprite;
        this.scale = playerScale;
    }

    public Sprite getSprite(double rotation) {
        return new Sprite(this.weaponSprite, width, height, scale, rotation);
    }

    public int getCurrent() {return this.currentFrame;}

}
