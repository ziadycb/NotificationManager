module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.datatransfer;
    requires java.desktop;
    requires org.json;
    requires Notify;
    requires mongo.java.driver;
    requires send.notification;
    requires javafx.swing;
    requires javafx.web;


    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}
