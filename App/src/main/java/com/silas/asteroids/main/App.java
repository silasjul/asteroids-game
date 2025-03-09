package com.silas.asteroids.main;

import com.silas.asteroids.entity.Entity;
import com.silas.asteroids.player.Player;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class App extends Application {
    private World world = new World();
    private int width = world.getWidth(), height = world.getHeight();
    private Player player = new Player(width/2, height/2);
    private final Canvas canvas = new Canvas(width, height);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private final StackPane root = new StackPane(canvas);
    private final Scene scene = new Scene(root, width, height);
    private final Timeline tl = new Timeline();
    private final Set<String> keyMap = new HashSet<>();

    private int frame = 0;

    @Override
    public void start(Stage stage) {
        KeyFrame kf = new KeyFrame(Duration.millis( 1000 / 144), event -> render()); // 144 fps
        tl.getKeyFrames().add(kf);
        tl.setCycleCount(Timeline.INDEFINITE);
        stage.setScene(scene);
        gc.setImageSmoothing(false); // ruins the pixelart when scaled
        stage.setResizable(false);
        stage.show();


        scene.setOnKeyPressed(keyEvent -> {
            keyMap.add(keyEvent.getCode().toString());
        });

        scene.setOnKeyReleased(keyEvent -> {
            keyMap.remove(keyEvent.getCode().toString());
        });

        // instantiate entities

        tl.play();
    }

    private void render() {
        update();
        draw();
        frame++;
    }

    private void update() {
        world.moveBackground();
        player.move(keyMap);
    }

    private void draw() {
        world.drawBackground(gc, frame);
        player.draw(gc);
    }

    public static void main(String[] args) {
        launch();
    }

}