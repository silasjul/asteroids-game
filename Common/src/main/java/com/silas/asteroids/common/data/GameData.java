package com.silas.asteroids.common.data;

import java.util.HashSet;
import java.util.Set;

public class GameData {

    double mouseX;
    double mouseY;
    boolean mousePressed;
    Set<String> keySet = new HashSet<>();
    int frame = 0;

    public double getMousePosX() {return this.mouseX;}
    public double getMousePosY() {return this.mouseY;}
    public Set<String> getKeySet() {return this.keySet;}
    public boolean getMousePressed() {return this.mousePressed;}
    public int getFrame() {return this.frame;}

    public void setMouseX(double mouseX) {this.mouseX = mouseX;}
    public void setMouseY(double mouseY) {this.mouseY = mouseY;}
    public void setMousePressed(boolean mousePressed) {this.mousePressed = mousePressed;}
    public void addKey(String key) {this.keySet.add(key);}
    public void removeKey(String key) {this.keySet.remove(key);}
    public void increaseFrame() {frame++;}
    public boolean isKeyPressed(String key) {return keySet.contains(key);}

}
