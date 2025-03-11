package com.silas.asteroids.sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Sprite
{
    private BufferedImage spriteSheet;
    private BufferedImage[] sprites;
    private Image[] fxSprites;
    private int width;
    private int height;
    private double scale;
    private int amount = 0;
    private int current = 0;

    public Sprite(String imagePath, int width, int height, double scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;

        try {
            spriteSheet = ImageIO.read(this.getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            System.out.println("image is null");
        }
        createSubImages();
    }

    public void createSubImages() {
        amount = spriteSheet.getWidth() / width;
        fxSprites = new Image[amount];
        sprites = new BufferedImage[amount];

        for (int i = 0; i < amount; i++) {
            BufferedImage newImage = spriteSheet.getSubimage(i*width, 0, width, height);
            sprites[i] = newImage;
            fxSprites[i] = SwingFXUtils.toFXImage(newImage, null); // Converts BufferedImage to a JavaFX Image
        }
    }

    public Image[] getFxSprites() {return fxSprites;}

    public Image getImage(int i) {
        return fxSprites[i];
    }

    public Image getImage(int i, double rotation) {
        BufferedImage selectedSprite = sprites[i];
        selectedSprite = scaleImage(selectedSprite);
        selectedSprite = rotateImage(selectedSprite, rotation);
        return SwingFXUtils.toFXImage(selectedSprite, null);
    }

    public BufferedImage scaleImage(BufferedImage img) {
        int w = (int) (img.getWidth()*scale);
        int h = (int) (img.getHeight()*scale);

        BufferedImage scaledImage = new BufferedImage(w, h, img.getType());
        Graphics2D g2d = scaledImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(img, 0, 0, w, h, null);
        g2d.dispose();

        return scaledImage;
    }

    public BufferedImage rotateImage(BufferedImage img, double angle) {
        int w = img.getWidth();
        int h = img.getHeight();

        // Create a new transparent image
        BufferedImage rotatedImage = new BufferedImage(w, h, img.getType());

        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // Apply transformation
        AffineTransform at = new AffineTransform();
        at.rotate(angle, w / 2.0, h / 2.0);  // Rotate around center
        g2d.setTransform(at);

        // Draw the original image onto the rotated transformation
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getCurrent() {return current;}
    public void next() {
        current++;
        if (current >= amount) {current = 0;}
    }

}
