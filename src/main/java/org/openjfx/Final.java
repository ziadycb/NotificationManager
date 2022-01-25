package org.openjfx;

import Controller.ApiCall;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Final {
    public ImageView Image1;
    public ImageView Image2;
    public ImageView Image3;
    public ImageView Image4;
    public Label Label1;
    public Label Label2;
    public Label Label4;
    public Label Label3;
    public Pane webContain;
    public javafx.scene.layout.BorderPane BorderPane;
    public Button invisibleButton;
    String ArticlesURL[];
    WebView browser;
    URL url;
    BufferedImage image ;
    javafx.scene.image.Image images;

    @FXML
    public void initialize() throws URISyntaxException, AWTException, IOException, InterruptedException, AWTException {
        ArticlesURL = new String[10];
        String s;
        String coin = SecondaryController.getCoinAPI();
        ApiCall a = new ApiCall();
        Scene c = App.getScene();
        Stage S = (Stage) c.getWindow();


        S.setWidth(1920);
        S.setHeight(1080);

        s = a.getArticles(coin);

        JSONArray obj = new JSONArray(s);
        JSONObject oje = new JSONObject();

        oje = obj.getJSONObject(0);
        setWeb(oje);

        ArticlesURL[0] = oje.getString("articleUrl");
        Image1.setImage(images);
        Label1.setText(oje.getString("articleTitle"));

        oje = obj.getJSONObject(1);
        setWeb(oje);

        ArticlesURL[1] = oje.getString("articleUrl");
        Image2.setImage(images);
        Label2.setText(oje.getString("articleTitle"));

        oje = obj.getJSONObject(2);
        setWeb(oje);

        ArticlesURL[2] = oje.getString("articleUrl");
        Image3.setImage(images);
        Label3.setText(oje.getString("articleTitle"));

        oje = obj.getJSONObject(3);
        setWeb(oje);

        ArticlesURL[3] = oje.getString("articleUrl");
        Image4.setImage(images);
        Label4.setText(oje.getString("articleTitle"));
    }

    public void setWeb(JSONObject oje) throws IOException {
        url = new URL(oje.getString("articleImage"));

        image = ImageIO.read(url);
        //image = scale(image,900,303);

        images = javafx.embed.swing.SwingFXUtils.toFXImage(image, null);

    }

    public static BufferedImage scale(BufferedImage src, int w, int h)
    {
        BufferedImage img =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

    public void Button1(ActionEvent actionEvent) {
        browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        Button b = (Button) actionEvent.getTarget();


        if(b.getText().equals("Button1"))
            webEngine.load(ArticlesURL[0]);
        else if(b.getText().equals("Button2"))
            webEngine.load(ArticlesURL[1]);
        else if(b.getText().equals("Button3"))
            webEngine.load(ArticlesURL[2]);
        else
            webEngine.load(ArticlesURL[3]);

        System.out.println("YAY");
        BorderPane.setCenter(browser);
        invisibleButton.setVisible(true);
        if(!BorderPane.isVisible())
            BorderPane.setVisible(true);
    }

    public void exitWeb(ActionEvent actionEvent) {
        browser.getEngine().load(null);
        BorderPane.setCenter(null);
        BorderPane.setVisible(false);
    }

    public void Button2(ActionEvent actionEvent) {
    }

    public void switchBack(ActionEvent actionEvent) throws IOException {
        App.setRoot("secondary");
    }
}
