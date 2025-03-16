package com.silas.asteroids.player;

import com.silas.asteroids.bullet.Bullet;
import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import com.silas.asteroids.common.entity.Character;
import com.silas.asteroids.common.entity.EntityType;
import com.silas.asteroids.player.engines.Engine;
import com.silas.asteroids.player.engines.EngineManager;
import com.silas.asteroids.player.weapons.Weapon;
import com.silas.asteroids.player.weapons.WeaponManager;
import com.silas.asteroids.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Player extends Character {
    private int xp = 0;
    private final EngineManager engineManager;
    private WeaponManager weaponManager;
    private Weapon weapon;
    private Engine engine;
    private boolean isMoving = false;

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

        // Engine
        this.engineManager = new EngineManager();
        this.engine = engineManager.getEngine(EngineManager.EngineType.CHARGED);

        Map<Integer, Weapon.FirePos> firePosMap = new HashMap<>();
        firePosMap.put(2, Weapon.FirePos.LEFT);
        firePosMap.put(3, Weapon.FirePos.RIGHT);
        this.weapon = new Weapon(firePosMap, "/player/weapons/gun/cannon.png", scale);
    }

    @Override
    public void update(World world, GameData gameData) {
        if (isDead) return;

        this.angle = calcAngle(gameData.getMousePosX(), gameData.getMousePosY());
        move(gameData, world);

        // Bullet logic
        if (gameData.getMousePressed() && isLoaded()) {
            fire(world, gameData);
        }
    }

    @Override
    protected void fire(World world, GameData gameData) {
        Bullet bullet = new Bullet(this.x, this.y, this.bulletSpeed, this.angle, Bullet.Type.BULLET, EntityType.PLAYERBULLET, this.scale);
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

        Sprite sprite = new Sprite (this.shipMap.get(c), this.width, this.height, this.scale, getImageRotation());
        return sprite.getSubImages(0);
    }

    @Override
    public void move(GameData gameData, World world)
    {
        double tempX = this.x;
        double tempY = this.y;
        // x-axis
        if (gameData.isKeyPressed("A")) x -= speed;
        if (gameData.isKeyPressed("D")) x += speed;

        // y-axis
        if (gameData.isKeyPressed("W")) y -= speed;
        if (gameData.isKeyPressed("S")) y += speed;

        // the player moved
        this.isMoving = this.x != tempX || this.y != tempY;
    }

    @Override
    public void draw(GraphicsContext gc, GameData gameData) {
        if (isDead) return;

        double rotation = this.getImageRotation();

        // draw engine flame
        Sprite flame = isMoving ? engine.getPowering(this.scale, rotation) : engine.getIdle(this.scale, rotation);
        int width = flame.getWidth();
        int height = flame.getHeight();
        double[] flamePos = getCenterImagePos(width, height);
        Image flameImage = flame.getSubImages(engine.getCurrent(gameData));
        gc.drawImage(flameImage, flamePos[0], flamePos[1], width*this.scale, height*this.scale);

        // draw engine
        Sprite base = engine.getBase(this.scale, rotation);
        width = base.getWidth();
        height = base.getHeight();
        double[] enginePos = getCenterImagePos(width, height);
        Image engineImage = base.getSubImages(base.getCurrent());
        gc.drawImage(engineImage, enginePos[0], enginePos[1], width*this.scale, height*this.scale);

        // draw weapon
        /*Sprite weaponSprite = this.weapon.getSprite(rotation);
        width = weaponSprite.getWidth();
        height = weaponSprite.getHeight();
        double[] weaponPos = getCenterImagePos(width, height);
        Image weaponImage = weaponSprite.getSubImages(this.weapon.getCurrent());
        gc.drawImage(weaponImage, weaponPos[0], weaponPos[1], width*this.scale, height*this.scale);*/


        // draw ship
        double[] pos = getCenterImagePos(this.width, this.height);
        gc.drawImage(getImg(), pos[0] , pos[1], this.width*scale, this.height*scale);

        // draw collision in testing
        if (gameData.isTesting()) drawCenterCollider(gc);
    }

    @Override
    public void die() {
        isDead = true;
    }

    private double[] getCenterImagePos(int width, int height) {
        double shipX = this.x - width * this.scale / 2;
        double shipY = this.y - height * this.scale / 2;

        return new double[]{shipX, shipY};
    }

    private double getImageRotation() {
        return this.angle + Math.PI/2;
    }

    private double[] getOffsetPosition(double offSet, double width, double height) {
        // returns an offset position from the center of the player to draw an image from based on rotation of player
        double engineX = getCenterX() + offSet * Math.cos(this.angle) - width/2.*this.scale;
        double engineY = getCenterY() + offSet * Math.sin(this.angle) - height/2.*this.scale;

        return new double[]{engineX, engineY};
    }
}
