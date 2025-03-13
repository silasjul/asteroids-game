package com.silas.asteroids.common.entity;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import javafx.scene.canvas.GraphicsContext;

public interface Entity {
    public void draw(GraphicsContext gc, GameData gameData);
    public void update(World world, GameData gameData);
    public EntityType getType();
}
