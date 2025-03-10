module App {
    requires Entity;
    requires Sprite;
    requires Player;
    requires javafx.swing;
    opens com.silas.asteroids.main to javafx.graphics;
}


