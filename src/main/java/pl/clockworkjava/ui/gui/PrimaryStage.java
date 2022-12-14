package pl.clockworkjava.ui.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.clockworkjava.util.Properties;

public class PrimaryStage {

    public void initialize (Stage primaryStage) {
        String hotelName = Properties.HOTEL_NAME;
        int systemVersion = Properties.SYSTEM_VERSION;

        MainTabView mainTabView = new MainTabView(primaryStage);

        Scene scene = new Scene(mainTabView.getMainTabs(), 640, 480);
        scene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());
        String tittle = String.format("System rezerwacji hotelu %s (%d)", hotelName, systemVersion);
        primaryStage.setTitle(tittle);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
