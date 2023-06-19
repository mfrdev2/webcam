package com.mfrdev.frabbi;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NonCancellableProgressIndicatorExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 400, 300);

        // Create the progress indicator
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setLayoutX(180);
        progressIndicator.setLayoutY(130);

        // Create an overlay pane to block user interaction
        Pane overlayPane = new Pane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent black background
        overlayPane.setDisable(true);
        overlayPane.setVisible(false); // Initially hidden

        // Show the progress indicator when needed
        showProgressIndicator(root, progressIndicator, overlayPane);

        // Simulate a long-running task
        simulateLongRunningTask(() -> {
            // Hide the progress indicator after the task is completed
            hideProgressIndicator(progressIndicator, overlayPane);
        });

        root.getChildren().addAll(overlayPane, progressIndicator);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to show the progress indicator and block user interaction
    private void showProgressIndicator(Pane root, ProgressIndicator progressIndicator, Pane overlayPane) {
        Platform.runLater(() -> {
            root.setDisable(true); // Disable the root pane to block user interaction
            overlayPane.setPrefSize(root.getWidth(), root.getHeight());
            overlayPane.setVisible(true);
            progressIndicator.setVisible(true);
        });
    }

    // Method to hide the progress indicator and enable user interaction
    private void hideProgressIndicator(ProgressIndicator progressIndicator, Pane overlayPane) {
        Platform.runLater(() -> {
            progressIndicator.setVisible(false);
            overlayPane.setVisible(false);
            Pane root = (Pane) progressIndicator.getParent();
            root.setDisable(false); // Enable the root pane to restore user interaction
        });
    }

    // Simulate a long-running task
    private void simulateLongRunningTask(Runnable task) {
        Thread thread = new Thread(() -> {
            try {
                // Simulate some long-running task
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            task.run();
        });
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
