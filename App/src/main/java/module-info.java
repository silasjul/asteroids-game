module App {
    requires Entity;
    requires Player;
    requires javafx.controls;
    opens com.silas.asteroids.main to javafx.graphics;
}


