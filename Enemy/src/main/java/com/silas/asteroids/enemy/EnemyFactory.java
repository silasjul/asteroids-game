package com.silas.asteroids.enemy;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import com.silas.asteroids.player.Player;

public class EnemyFactory {
    int maxEnemies = 10;
    int spawnRate = 5;
    double lastSpawnTime = 0;
    Player player;

    public enum Type {
        MELEE,
    }

    public EnemyFactory(Player player) {
        this.player = player;
    }

    public void spawn(World world, GameData gameData) {
        if (getLastSpawnSeconds() >= spawnRate && world.getEnemiesAmount() < this.maxEnemies) {
            spawnEnemy(Type.MELEE, getSpawnPosX(world), getSpawnPosY(world), world);
            lastSpawnTime = System.currentTimeMillis() / 1000.;
        }
    }

    private void spawnEnemy(Type type, double x, double y, World world) {
        System.out.println(x +" "+ y);
        switch (type) {
            case MELEE -> world.addEntity(new Melee(x, y, player));
        }
    }

    private int getLastSpawnSeconds() {
        return (int) (System.currentTimeMillis() / 1000. - this.lastSpawnTime);
    }

    private double getSpawnPosX(World world) {return Math.random() * (world.getWidth() - 200) + 200;}
    private double getSpawnPosY(World world) {return Math.random() * (world.getHeight() - 200) + 200;}
}
