package com.silas.asteroids.main;

import com.silas.asteroids.entity.Entity;
import com.silas.asteroids.player.Player;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
    private int width = 800, height = 800;

    private final Canvas canvas = new Canvas(width, height);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private final StackPane root = new StackPane();
    private final Scene scene = new Scene(root, width, height);
    private final Timeline tl = new Timeline();

    @Override
    public void start(Stage stage) {
        KeyFrame kf = new KeyFrame(Duration.millis(5), event -> render());
        tl.getKeyFrames().add(kf);
        tl.setCycleCount(Timeline.INDEFINITE);
        stage.setScene(scene);
        stage.show();

        Entity.helloEntity();
        Player.helloPlayer();

        // instantiate entities

        tl.play();
    }

    private void render() {
        update();
        draw();
    }

    private void update() {}

    private void draw() {}

    public static void main(String[] args) {
        launch();
    }

}