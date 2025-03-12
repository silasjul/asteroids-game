package com.silas.asteroids.enemy;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import com.silas.asteroids.common.entity.Character;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A close ranged enemy that seeks towards the player and damages the player at close range
 *
 */
public class Melee extends Character
{
    public Melee(int width, int height, double scale, double x, double y, int hp, int fireRate, int bulletSpeed) {
        super(width, height, scale, x, y, hp, fireRate, bulletSpeed);
    }

    public static void main(String[] args )
    {
        System.out.println( "Hello World!" );
    }

    @Override
    public void update(World world, GameData gameData) {

    }

    @Override
    protected void fire(World world) {

    }

    @Override
    protected Image getImg() {
        return null;
    }

    @Override
    protected void move(GameData gameData) {

    }

    @Override
    public void draw(GraphicsContext gc, GameData gameData) {

    }
}
