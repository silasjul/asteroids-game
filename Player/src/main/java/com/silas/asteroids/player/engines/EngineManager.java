package com.silas.asteroids.player.engines;

public class EngineManager {
    private Engine engine;


    public enum EngineType {
        BASE,
        CHARGED,
        PULSE,
        BURST
    }

    public Engine getEngine(EngineType type) {
        if (EngineType.BASE == type) {
            this.engine = new Engine("/player/engines/base");
        }
        else if (EngineType.CHARGED == type) {
            this.engine = new Engine("/player/engines/charged");
        }
        else if (EngineType.PULSE == type) {
            this.engine = new Engine("/player/engines/pulse");
        }
        else if (EngineType.BURST == type) {
            this.engine = new Engine("/player/engines/burst");
        }

        return this.engine;
    }
}
