package Controller;


import Model.SubscribeManager;
import org.bson.Document;
import org.json.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiCall {

    Image image;
    SystemTray tray;
    TrayIcon trayIcon;
    boolean exist;
    SubscribeManager s;

    private Document document;
    public static void threadTest()
    {
        System.out.println("yay");
    }

    public ApiCall() throws AWTException, URISyntaxException {
        //Obtain only one instance of the SystemTray object
        tray = SystemTray.getSystemTray();

        //If the icon is a file
        image = Toolkit.getDefaultToolkit().createImage("../sample/Assets/bitcoin-icon-png-42940-Windows.ico");
        exist = false;
    }

    public String getArticles(String coin) throws IOException, InterruptedException, AWTException, URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://latest-crypto-news.p.rapidapi.com/newsbtc/" + coin + "/latest"))
                .header("x-rapidapi-host", "latest-crypto-news.p.rapidapi.com")
                .header("x-rapidapi-key", "dd577d6511mshf4742c792d63926p154652jsn862655cd3f58")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return response.body();
    }

    public  void newsManager(SubscribeManager s) throws IOException, URISyntaxException, InterruptedException, AWTException {
        List<String> sub = s.returnSub();
        this.s = s;

        if(sub.contains("BTC"))
            getNews("bitcoin");
        if(sub.contains("ETH"))
            getNews("ethereum");
        if(sub.contains("DOGE"))
            getNews("dogecoin");
        if(sub.contains("ADA"))
            getNews("cardano");
        if(sub.contains("XRP"))
            getNews("ripple");
    }

    public void getNews(String coin) throws IOException, InterruptedException, AWTException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://latest-crypto-news.p.rapidapi.com/newsbtc/"+coin+"/latest"))
                .header("x-rapidapi-host", "latest-crypto-news.p.rapidapi.com")
                .header("x-rapidapi-key", "dd577d6511mshf4742c792d63926p154652jsn862655cd3f58")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if(coin.equals("bitcoin"))
            coin = "BTC";
        if(coin.equals("ethereum"))
            coin = "ETH";
        if(coin.equals("dogecoin"))
            coin = "DOGE";
        if(coin.equals("cardano"))
            coin = "ADA";
        if(coin.equals("ripple"))
            coin = "XRP";

        breakMessage(response,coin);

    }



    public void breakMessage(HttpResponse<String> response,String coin) throws IOException, InterruptedException {
        JSONArray obj = new JSONArray(response.body());
        JSONObject oje = new JSONObject();
        JSONObject ojes = new JSONObject();

        System.out.println("yay");

        List<String> articles = s.returnArticles(coin);
        boolean empty = articles.isEmpty();
        System.out.println("yay");

        for (int i = obj.length()-1; i >= 0; i--)
        {
            System.out.println(i);
            oje = obj.getJSONObject(i);

            if(empty)
            {
                s.insertArticle(coin, oje.getString("articleTitle"));
            }
            else {
                if (articles.contains(oje.getString("articleTitle"))) {

                } else {
                    displayPowerShell(oje);
                    s.insertArticle(coin, oje.getString("articleTitle"));
                    s.removeArticle(coin);
                }
            }
        }

        //displayPowerShell(oje);
        System.out.println(obj);
    }


    public  void displayPowerShell(JSONObject obj) throws IOException, InterruptedException {
        String url = "'" + obj.getString("articleUrl") + "'";
        String text = obj.getString("articleTitle");

        text = text.replaceAll("’","");
        text = "'" + text + "'";

        ProcessBuilder builder = new ProcessBuilder("powershell.exe",
                "New-BurntToastNotification","-Text",text, "-Button", "@(New-BTButton -Content 'Blog' -Arguments",url+")");
        Process p = builder.inheritIO().start();
        int exitCode = p.waitFor();
    }
/*
    public void displayNotification(JSONObject obj) throws MalformedURLException {

        Notify.create()
                .title("BTC")
                .text(obj.getString("articleTitle"))
                .position(Pos.BOTTOM_RIGHT)
                .onAction( new ActionHandler<Notify>() {
                    @Override
                    public void handle(Notify value) {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            try {
                                Desktop.getDesktop().browse(new URI(obj.getString("articleUrl")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .hideAfter(5000)
                .darkStyle()      // There are two default themes darkStyle() and default.
                .showWarning();;   // You can use warnings and error as well.
        Toolkit.getDefaultToolkit().beep();
    }

    public  void displayTray(JSONObject obj) throws AWTException, IOException, URISyntaxException {

        if (exist) {
            tray.remove(trayIcon);
            exist = false;
        }
        exist = true;
        trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("Crypto News");

        tray.add(trayIcon);


        URL url = new URL(obj.getString("articleUrl"));
        URI uri = url.toURI();

        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

        trayIcon.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                try {

                    desktop.browse(uri);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }

        });

        trayIcon.displayMessage("BTC News", obj.getString("articleTitle"), MessageType.INFO);

    }
*/
}


