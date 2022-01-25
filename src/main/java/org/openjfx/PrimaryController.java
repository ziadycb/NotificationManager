package org.openjfx;

import java.awt.*;
import java.io.IOException;

import Model.RegisterLogin;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class PrimaryController {

    static String username;
    static String password;
    public TextField Username;
    public PasswordField Password;
    @FXML private Button Switch ;
    @FXML private Button LoginRegister;

    @FXML
    private void switchToSecondary() throws IOException {

        RegisterLogin r = new RegisterLogin(Username.getText(),Password.getText());
        if(r.Login()) {
            username = Username.getText();
            password = Password.getText();
            App.setRoot("secondary");
            Scene scene = App.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.setResizable(true);
            stage.setFullScreen(true);


        }
        System.out.println("Error");

    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("third");
    }

}
