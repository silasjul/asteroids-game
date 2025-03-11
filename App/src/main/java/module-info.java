module App {
    requires Player;
    requires Common;
    requires javafx.swing;
    opens com.silas.asteroids.main to javafx.graphics;
}


