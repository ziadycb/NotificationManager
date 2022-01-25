package org.openjfx;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Controller.ApiCall;
import Model.SubscribeManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;



public class SecondaryController  {

    static String coinAPI;
    public String username;
    public String password;
    public Label SubscribeBTC;
    public Label SubscribeETH;
    public Label SubscribeDOGE;
    public Label SubscribeADA;
    public Label SubscribeXRP;
    public Label SubscribeNFT;
    private SubscribeManager s;

    @FXML
    public void initialize() throws URISyntaxException, AWTException {
        username = PrimaryController.getUsername();
        password = PrimaryController.getPassword();

        s = new SubscribeManager(username,password);

        List<String> sub = s.returnSub();
        App.startExec(s);

        if(sub.contains("BTC"))
            SubscribeBTC.setText("Unsubscribe");
        if(sub.contains("ETH"))
            SubscribeETH.setText("Unsubscribe");
        if(sub.contains("DOGE"))
            SubscribeDOGE.setText("Unsubscribe");
        if(sub.contains("ADA"))
            SubscribeADA.setText("Unsubscribe");
        if(sub.contains("XRP"))
            SubscribeXRP.setText("Unsubscribe");


    }

    public static String getCoinAPI() {
        return coinAPI;
    }

    public void addDOGE(ActionEvent actionEvent) throws URISyntaxException, AWTException {
        if(SubscribeDOGE.getText().equals("Subscribe")) {
            s.manageSub("DOGE",true);
            SubscribeDOGE.setText("Unsubscribe");
        }
        else
        {
            s.manageSub("DOGE",false);
            SubscribeDOGE.setText("Subscribe");
        }

    }

    public void addETH(ActionEvent actionEvent) throws URISyntaxException, AWTException {
        if(SubscribeETH.getText().equals("Subscribe")) {
            s.manageSub("ETH",true);
            SubscribeETH.setText("Unsubscribe");
        }
        else
        {
            s.manageSub("ETH",false);
            SubscribeETH.setText("Subscribe");
        }

    }

    public void addBTC(ActionEvent actionEvent) throws URISyntaxException, AWTException {
        if(SubscribeBTC.getText().equals("Subscribe")) {
            s.manageSub("BTC",true);
            SubscribeBTC.setText("Unsubscribe");
        }
        else
        {
            s.manageSub("BTC",false);
            SubscribeBTC.setText("Subscribe");
        }

    }

    public void addADA(ActionEvent actionEvent) throws URISyntaxException, AWTException {
        if(SubscribeADA.getText().equals("Subscribe")) {
            s.manageSub("ADA",true);
            SubscribeADA.setText("Unsubscribe");
        }
        else
        {
            s.manageSub("ADA",false);
            SubscribeADA.setText("Subscribe");
        }

    }

    public void addXRP(ActionEvent actionEvent) throws URISyntaxException, AWTException {
        if(SubscribeXRP.getText().equals("Subscribe")) {
            s.manageSub("XRP",true);
            SubscribeXRP.setText("Unsubscribe");
        }
        else
        {
            s.manageSub("XRP",false);
            SubscribeXRP.setText("Subscribe");
        }

    }

    public void displayArticles(ActionEvent actionEvent) throws IOException {

        Button temp = (Button) actionEvent.getTarget();
        coinAPI = temp.getId();
        App.setRoot("final");
    }
}