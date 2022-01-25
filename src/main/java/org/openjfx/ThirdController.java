package org.openjfx;

import java.awt.*;
import java.io.IOException;

import Model.RegisterLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class ThirdController {

    public TextField Username;
    public PasswordField Password;

    @FXML
    private void RegisterUser() throws IOException {

        RegisterLogin r = new RegisterLogin(Username.getText(),Password.getText());
        r.Register();

    }

    public void SwitchLogin(ActionEvent actionEvent) throws IOException {
        App.setRoot("primary");
    }
}
