package com.silas.asteroids.enemy;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import com.silas.asteroids.player.Player;

public class EnemyFactory {
    int maxEnemies = 10;
    double spawnRate = .5;
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
            double[] spawnPos = getSpawnPos(world);
            spawnEnemy(Type.MELEE, spawnPos[0], spawnPos[1], world);
            lastSpawnTime = System.currentTimeMillis() / 1000.;
        }
    }

    private void spawnEnemy(Type type, double x, double y, World world) {
        switch (type) {
            case MELEE -> world.addEntity(new Melee(x, y, player));
        }
    }

    private int getLastSpawnSeconds() {
        return (int) (System.currentTimeMillis() / 1000. - this.lastSpawnTime);
    }

    private double[] getSpawnPos(World world) {
        double[] spawnPos = new double[2];

        double sideToSpawn = Math.random();

        // spawn topside
        if (sideToSpawn <= 0.25) {
            spawnPos[0] = Math.random() * world.getWidth();
            spawnPos[1] = -50;
        }

        // spawn bottom
        else if (sideToSpawn <= 0.50) {
            spawnPos[0] = Math.random() * world.getWidth();
            spawnPos[1] = world.getHeight() + 10;
        }

        // spawn right
        else if (sideToSpawn <= 0.75) {
            spawnPos[0] = world.getWidth() + 10;
            spawnPos[1] = Math.random() * world.getHeight();
        }

        // spawn left
        else {
            spawnPos[0] = -50;
            spawnPos[1] = Math.random() * world.getHeight();
        }

        return spawnPos;
    }
}
