module com.example.zipaboutgui {

    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;

    // Allow JavaFX to access controllers via reflection
    opens com.example.zipaboutgui.ui.controller to javafx.fxml;

    // Export only REAL packages (not parent folders)
    exports com.example.zipaboutgui.app;
    exports com.example.zipaboutgui.service;
    exports com.example.zipaboutgui.domain.vehicle;
    exports com.example.zipaboutgui.domain.user;
}
