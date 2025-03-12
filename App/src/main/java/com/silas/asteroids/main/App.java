package com.silas.asteroids.main;

import com.silas.asteroids.common.data.GameData;
import com.silas.asteroids.common.data.World;
import com.silas.asteroids.player.Player;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class App extends Application {
    private final World world = new World();
    private final GameData gameData = new GameData();

    private final int width = world.getWidth();
    private final int height = world.getHeight();

    private final Player player = new Player(width, height);

    private final Canvas canvas = new Canvas(width, height);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final StackPane root = new StackPane(canvas);
    private final Scene scene = new Scene(root, width, height);
    private final Timeline tl = new Timeline();


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        KeyFrame kf = new KeyFrame(Duration.millis( (double) 1000 / 144), _ -> render()); // 144 fps
        tl.getKeyFrames().add(kf);
        tl.setCycleCount(Timeline.INDEFINITE);
        stage.setScene(scene);
        gc.setImageSmoothing(false); // ruins the pixelart when scaled
        stage.setResizable(false);
        stage.show();

        scene.setCursor(Cursor.CROSSHAIR);

        scene.setOnKeyPressed(keyEvent -> gameData.addKey(keyEvent.getCode().toString()));
        scene.setOnKeyReleased(keyEvent -> gameData.removeKey(keyEvent.getCode().toString()));

        scene.setOnMouseMoved(mouseEvent -> {
            gameData.setMouseX(mouseEvent.getX());
            gameData.setMouseY(mouseEvent.getY());
        });
        scene.setOnMouseDragged(mouseEvent -> {
            gameData.setMouseX(mouseEvent.getX());
            gameData.setMouseY(mouseEvent.getY());
        });

        scene.setOnMousePressed(_ -> gameData.setMousePressed(true));
        scene.setOnMouseReleased(_ -> gameData.setMousePressed(false));

        // instantiate entities

        tl.play();
    }

    private void render() {
        update();
        draw();
        gameData.increaseFrame();
    }

    private void update() {
        world.moveBackground();
        player.update(world, gameData);
        world.updateEntities(gameData);
    }

    private void draw() {
        world.drawBackground(gc, gameData);
        world.drawEntities(gc, gameData);
        player.draw(gc, gameData);
    }

}