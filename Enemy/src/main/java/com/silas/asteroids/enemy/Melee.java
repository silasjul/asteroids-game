package com.silas.asteroids.enemy;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.entity.Character;
import com.silas.asteroids.common.entity.EntityType;
import com.silas.asteroids.player.Player;
import com.silas.asteroids.sprite.Sprite;
import com.silas.asteroids.common.data.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A close ranged enemy that seeks towards the player and damages the player at close range
 *
 */
public class Melee extends Character
{
    int attackRange = 10;
    Player player;
    int dmg = 2;
    int width = 64;
    int height = 64;
    Sprite sprite = new Sprite("/melee/ships.png", width, height, scale, 0);

    public Melee(double x, double y, Player player) {
        super(64, 64, 1.8, x, y, 45, 40,20, 1, 1, 0);
        this.player = player;
    }

    @Override
    public void update(World world, GameData gameData) {
        this.move(gameData);


        if (System.currentTimeMillis() - lastFire > getFireDelay() && true) {
            this.fire(world, gameData);
            lastFire = System.currentTimeMillis();
        }
    }

    @Override
    public EntityType getType() {
        return EntityType.ENEMY;
    }

    @Override
    protected Image getImg() {return sprite.getCurrentImage();}

    @Override
    protected void move(GameData gameData) {
        double angleRad = calcAngle(player.getCenterX(), player.getCenterY());
        this.x = this.x + speed * Math.cos(angleRad);
        this.y = this.y + speed * Math.sin(angleRad);
    }

    @Override
    protected void fire(World world, GameData gameData) {
        player.takeDmg(this.dmg);
    }

    @Override
    public void draw(GraphicsContext gc, GameData gameData) {
        gc.drawImage(sprite.getCurrentImage(), x-this.width/2.*scale, y-this.height/2.*scale, this.width*scale, this.height*scale);

        if (gameData.isTesting()) drawCenterCollider(gc);
    }
}
