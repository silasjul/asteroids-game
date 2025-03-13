package com.silas.asteroids.enemy;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.entity.Character;
import com.silas.asteroids.common.entity.Entity;
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
    protected Player player;
    protected Sprite sprite;
    protected Sprite deathAnimation;

    public Melee(double x, double y, Player player) {
        super(x, y,64, 64,45, 40, EntityType.ENEMY, 1.8,20,5, 1, 1, 0);
        this.player = player;

        this.sprite = new Sprite("/melee/ships.png", width, height, scale, 0);
        this.deathAnimation = new Sprite("/melee/death.png", width, height, scale, 0);
    }

    @Override
    public void update(World world, GameData gameData) {
        if (isDead) {
            if (gameData.isAnimationFrame()) deathAnimation.next();
            if (deathAnimation.getAnimationCount() > 0) world.removeEntity(this);
            return;
        }

        this.move(gameData);

        // Attack player on collision
        if (isLoaded() && world.isColliding(this, player)) this.fire(world, gameData);

        // Hit by player bullet
        for (Entity bullet : world.getPlayerBullets()) {
            if (world.isColliding(this, bullet)) {
                world.removeEntity(bullet);
                this.takeDmg(player.getDmg());
            }
        }
    }

    @Override
    protected Image getImg() {
        if (isDead) return deathAnimation.getCurrentImage();
        return sprite.getCurrentImage();}

    @Override
    protected void move(GameData gameData) {
        double angleRad = calcAngle(player.getCenterX(), player.getCenterY());
        this.x = this.x + speed * Math.cos(angleRad);
        this.y = this.y + speed * Math.sin(angleRad);
    }

    @Override
    protected void fire(World world, GameData gameData) {
        player.takeDmg(this.dmg);
        lastFire = System.currentTimeMillis();
    }

    @Override
    public void draw(GraphicsContext gc, GameData gameData) {
        gc.drawImage(this.getImg(), x-this.width/2.*scale, y-this.height/2.*scale, this.width*scale, this.height*scale);

        if (gameData.isTesting()) drawCenterCollider(gc);
    }

    @Override
    public void die() {
        this.isDead = true;
    }
}
