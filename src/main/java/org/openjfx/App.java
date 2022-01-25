package org.openjfx;

import Controller.ApiCall;
import Model.SubscribeManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



import fr.jcgay.notification.Icon;
import fr.jcgay.notification.Notification;
import fr.jcgay.notification.Notifier;
import fr.jcgay.notification.SendNotification;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * JavaFX App
 */
public class App extends Application {
    static ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 841, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        this.stage = stage;
    }


    public static Scene getScene() {
        return scene;
    }

    static void changeStageSize(Stage stage, int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    static void startExec(SubscribeManager s) throws URISyntaxException, AWTException {
        ApiCall a = new ApiCall();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    a.newsManager(s);
                } catch (IOException | InterruptedException | AWTException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 30, TimeUnit.SECONDS);
    }



    public static void main(String[] args) throws URISyntaxException, AWTException, InterruptedException, IOException {
        launch(args);

        Thread.sleep(3000000);
        exec.shutdown();

    }

}