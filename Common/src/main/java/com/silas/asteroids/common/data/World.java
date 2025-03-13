package com.silas.asteroids.common.data;

import com.silas.asteroids.common.entity.Character;
import com.silas.asteroids.common.entity.Entity;
import com.silas.asteroids.common.entity.EntityType;
import com.silas.asteroids.sprite.Parallax;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Contains entities, background and window dimensions
 *
 */
public class World {
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Entity> toRemove = new ArrayList<>();
    private final int width = 640;
    private final int height = 360;
    private final int scale = 2;
    private int planetScale = 5;
    private final Parallax planet = new Parallax("/background/planet.png", 96, 96, 1, scale);
    private final Parallax[] bg = new Parallax[] {
            new Parallax("/background/void.png", width, height, 0.2, scale),
            new Parallax("/background/stars.png", width, height, 0.35, scale),
            new Parallax("/background/bigStarts.png", width, height, 0.5, scale),
    };

    public World() {
        planet.setOffsetY(-planet.getHeight()*planetScale-500);
        planet.setOffsetX(Math.random()*(width*scale));
    }

    // ENTITIES

    public void updateEntities(GameData gameData) {
        for (Entity entity : entities) entity.update(this, gameData);

        // Avoids concurrent modification
        entities.removeAll(toRemove);
        toRemove.clear();
    }

    public void drawEntities(GraphicsContext gc, GameData gameData) {
        for (Entity entity : entities) entity.draw(gc, gameData);
    }


    // BACKGROUND

    public void moveBackground() {
        for (Parallax layer : bg) {
            layer.move();
            if (layer.getOffsetY() >= height * scale) layer.setOffsetY(0);
        }

        // Random planet fly-by
        planet.move();
        if (planet.getOffsetY() > height*scale) {
            planet.setOffsetX(Math.random()*(width*scale-planet.getWidth()*planetScale));
            planetScale = (int) (Math.random() * 10);
            planet.setOffsetY(-planet.getHeight()*planetScale-800);
        }
    }

    public void drawBackground(GraphicsContext gc, GameData gameData) {
        for (Parallax parallax : bg) {
            if (gameData.isAnimationFrame()) {
                parallax.next(); // point to next image
                planet.next();
            }

            Image img = parallax.getCurrentImage();
            gc.drawImage(img, 0, parallax.getOffsetY(), width * scale, height * scale);
            gc.drawImage(img, 0, parallax.getOffsetY() - height * scale, width * scale, height * scale);

        }
        gc.drawImage(planet.getCurrentImage(), planet.getOffsetX(), planet.getOffsetY(), planet.getWidth()*planetScale, planet.getHeight()*planetScale);
    }


    public void addEntity(Entity entity) {
        entities.add(entity);}
    public void removeEntity(Entity entity) {toRemove.add(entity);}

    public int getWidth() {return width*scale;}
    public int getHeight() {return height*scale;}
    public int getEnemiesAmount() {
        int enemyCount = 0;
        for (Entity entity : entities) {
            if (entity instanceof Character) enemyCount++;
        }
        return enemyCount;
    }

    public ArrayList<Entity> getPlayerBullets() {
        ArrayList<Entity> bullets = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getType() == EntityType.PLAYERBULLET) bullets.add(entity);
        }
        return bullets;
    }

    public boolean isColliding(Entity a, Entity b) {
        return a.getX() < b.getX() + b.getColliderWidth() &&
                a.getX() + a.getColliderWidth() > b.getX() &&
                a.getY() < b.getY() + b.getColliderHeight() &&
                a.getY() + a.getColliderHeight() > b.getY();
    }
}
