package com.example.zipaboutgui.ui.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class responsible for switching JavaFX scenes.
 *
 * <p>This class centralizes all scene-switching logic
 * so that controllers do not need to manage:</p>
 * <ul>
 *   <li>The JavaFX {@link Stage}</li>
 *   <li>{@link FXMLLoader} creation</li>
 *   <li>FXML resource paths</li>
 * </ul>
 *
 * <p>This keeps controllers lightweight and focused
 * purely on UI behavior.</p>
 */
public class SceneSwitcher {

    /**
     * Primary stage of the JavaFX application.
     * Set once at application startup.
     */
    private static Stage stage;

    /**
     * Stores the primary stage when the application starts.
     *
     * <p>This method is called from {@code ZipAboutApp}
     * during initialization.</p>
     *
     * @param primaryStage the main JavaFX stage
     */
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * Switches the current scene to the specified FXML file.
     *
     * <p>The FXML file is loaded from the
     * {@code /ui/view/} package.</p>
     *
     * @param fxmlFile the name of the FXML file
     *                 (e.g. {@code "login.fxml"})
     */
    public static void switchTo(String fxmlFile) {
        try {
            // Build the absolute path to the FXML resource
            String path =
                    "/com/example/zipaboutgui/ui/view/" + fxmlFile;

            // Load the FXML
            FXMLLoader loader = new FXMLLoader(
                    SceneSwitcher.class.getResource(path)
            );

            // Fail fast if the resource cannot be found
            if (loader.getLocation() == null) {
                throw new IllegalStateException(
                        "FXML not found at: " + path
                );
            }

            // Create and set the new scene
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e) {
            // Print stack trace for debugging
            e.printStackTrace();
        }
    }
}
