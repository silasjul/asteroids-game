package com.silas.asteroids.main;

import com.silas.asteroids.common.World;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class App extends Application {
    private final World world = new World();
    private final int width = world.getWidth();
    private final int height = world.getHeight();
    private final Player player = new Player(width/2, height/2);
    private final Canvas canvas = new Canvas(width, height);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final StackPane root = new StackPane(canvas);
    private final Scene scene = new Scene(root, width, height);
    private final Timeline tl = new Timeline();

    private final Set<String> keyMap = new HashSet<>();
    private final HashMap<String, Double> mousePos = new HashMap<>();
    private boolean mousePressed = false;
    private int frame = 0;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        KeyFrame kf = new KeyFrame(Duration.millis( (double) 1000 / 144), event -> render()); // 144 fps
        tl.getKeyFrames().add(kf);
        tl.setCycleCount(Timeline.INDEFINITE);
        stage.setScene(scene);
        gc.setImageSmoothing(false); // ruins the pixelart when scaled
        stage.setResizable(false);
        stage.show();

        scene.setCursor(Cursor.CROSSHAIR);

        scene.setOnKeyPressed(keyEvent -> {
            keyMap.add(keyEvent.getCode().toString());
        });

        scene.setOnKeyReleased(keyEvent -> {
            keyMap.remove(keyEvent.getCode().toString());
        });

        scene.setOnMouseMoved(mouseEvent -> {
            mousePos.put("X", mouseEvent.getX());
            mousePos.put("Y", mouseEvent.getY());
        });

        scene.setOnMouseDragged(mouseEvent -> {
            mousePos.put("X", mouseEvent.getX());
            mousePos.put("Y", mouseEvent.getY());
        });

        scene.setOnMousePressed(_ -> mousePressed=true);
        scene.setOnMouseReleased(_ -> mousePressed=false);

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
        player.update(mousePos.get("X"), mousePos.get("Y"), keyMap);
    }

    private void draw() {
        world.drawBackground(gc, frame);
        player.draw(gc);
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public HashMap<String, Double> getMousePos() {
        return mousePos;
    }

}