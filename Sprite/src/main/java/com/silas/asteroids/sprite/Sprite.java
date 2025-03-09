package com.silas.asteroids.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Sprite
{
    private BufferedImage bi;
    private Image[] subImages;
    private int width;
    private int height;
    private int amount = 0;
    private int current = 0;

    public Sprite(String imagePath, int width, int height) {
        this.width = width;
        this.height = height;
        try {
            bi = ImageIO.read(this.getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            System.out.println("image is null");
        }
        createSubImages();
    }

    public void createSubImages() {
        amount = bi.getWidth() / width;
        subImages = new Image[amount];

        for (int i = 0; i < amount; i++) {
            BufferedImage newImage = bi.getSubimage(i*width, 0, width, height);
            subImages[i] = SwingFXUtils.toFXImage(newImage, null); // Converts BufferedImage to a JavaFX Image
        }
    }

    public Image[] getSubImages() {return subImages;}

    public Image getImage(int i) {
        if (i >= 0 || i < amount) {
            return subImages[i];
        }
        return null;
    }

    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getCurrent() {return current;}
    public void next() {
        current++;
        if (current >= amount) {current = 0;}
    }

}
