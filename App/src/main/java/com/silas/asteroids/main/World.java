package com.silas.asteroids.main;

import com.silas.asteroids.entity.Entity;
import com.silas.asteroids.sprite.Parallax;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class World {
    private ArrayList<Entity> entities = new ArrayList<>();
    private int width = 640, height = 360;
    private int scale = 2;
    private int planetScale = 5;
    private Parallax planet = new Parallax("/background/planet.png", 96, 96, 1);
    private Parallax[] bg = new Parallax[] {
            new Parallax("/background/void.png", width, height, 0.2),
            new Parallax("/background/stars.png", width, height, 0.35),
            new Parallax("/background/bigStarts.png", width, height, 0.5),
    };

    public World() {
        planet.setOffsetY(-planet.getHeight()*planetScale-500);
        planet.setOffsetX(Math.random()*(width*scale));
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void moveBackground() {
        for (int i = 0; i < bg.length; i++) {
            Parallax layer = bg[i];
            layer.move();
            if (layer.getOffsetY() >= height*scale) layer.setOffsetY(0);
        }

        // Random planet fly-by
        planet.move();
        if (planet.getOffsetY() > height*scale) {
            planet.setOffsetX(Math.random()*(width*scale-planet.getWidth()*planetScale));
            planetScale = (int) (Math.random() * 10);
            planet.setOffsetY(-planet.getHeight()*planetScale-800);
        };
    }

    public void drawBackground(GraphicsContext gc, int frame) {
        for (int i = 0; i < bg.length; i++) {
            if (frame % 14 == 0) { // slow down animation
                bg[i].next(); // point to next image
                planet.next();
            }

            Parallax layer = bg[i];
            Image img = layer.getImage(layer.getCurrent());
            gc.drawImage(img,0,layer.getOffsetY(), width*scale, height*scale);
            gc.drawImage(img,0,layer.getOffsetY()-height*scale, width*scale, height*scale);

        }
        gc.drawImage(planet.getImage(planet.getCurrent()), planet.getOffsetX(), planet.getOffsetY(), planet.getWidth()*planetScale, planet.getHeight()*planetScale);
    }

    public int getWidth() {return width*scale;}

    public int getHeight() {return height*scale;}
}
