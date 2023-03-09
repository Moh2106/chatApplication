module ma.enset.chatapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens ma.enset.chatapplication.controllers to javafx.fxml;
    opens ma.enset.chatapplication to javafx.fxml;

    exports ma.enset.chatapplication;
    exports ma.enset.chatapplication.controllers to javafx.fxml;
}