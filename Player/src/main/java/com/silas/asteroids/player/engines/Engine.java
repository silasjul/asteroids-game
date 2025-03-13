package com.silas.asteroids.player.engines;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.sprite.Sprite;

public class Engine {
    private String enginePath;
    private int spriteLength;
    private int current = 0;

    public Engine(String enginePath) {
        this.enginePath = enginePath;
    }

    public Sprite getBase(double playerScale, double playerRotation) {
        return new Sprite(enginePath+"/base.png", 48, 48, playerScale, playerRotation);
    }

    public Sprite getIdle(double playerScale, double playerRotation) {
        Sprite idle = new Sprite(enginePath+"/idle.png", 48, 48, playerScale, playerRotation);
        spriteLength = idle.getAmount();
        return idle;
    }

    public Sprite getPowering(double playerScale, double playerRotation) {
        Sprite powering = new Sprite(enginePath+"/powering.png", 48, 48, playerScale, playerRotation);
        spriteLength = powering.getAmount();
        return powering;
    }

    public int getCurrent(GameData gameData) {
        if (current >= spriteLength-1) current = 0;
        int temp = current;
        if (gameData.isAnimationFrame()) current++;
        return temp;
    }
}
