module App {
    requires Player;
    requires javafx.swing;
    requires Enemy;
    requires Common;
    opens com.silas.asteroids.main to javafx.graphics;
}


