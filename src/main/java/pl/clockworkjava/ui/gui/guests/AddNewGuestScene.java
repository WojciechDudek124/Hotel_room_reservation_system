package pl.clockworkjava.ui.gui.guests;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.clockworkjava.domain.ObjectPool;
import pl.clockworkjava.domain.guest.GuestService;
import pl.clockworkjava.domain.guest.dto.GuestDTO;
import pl.clockworkjava.domain.room.dto.RoomDTO;
import pl.clockworkjava.util.Properties;


import java.util.ArrayList;
import java.util.List;

public class AddNewGuestScene {

    private Scene mainScene;

    private final GuestService guestService = ObjectPool.getGuestService();
    private final List<ComboBox<String>> comboBoxes = new ArrayList<>();


    public AddNewGuestScene(Stage modalStage, TableView<GuestDTO> guestTableView) {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);

        Label guestFirstNameLabel = new Label("Imie:");
        TextField guestFirstNameField = new TextField();

        guestFirstNameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\p{L}*")){
                guestFirstNameField.setText(oldValue);
            }
        });

        gridPane.add(guestFirstNameLabel, 0,0);
        gridPane.add(guestFirstNameField, 1,0);

        Label guestLastNameLabel = new Label("Nazwisko:");
        TextField guestLastNameField = new TextField();

        guestLastNameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\p{L}*")){
                guestLastNameField.setText(oldValue);
            }
        });

        gridPane.add(guestLastNameLabel, 0,1);
        gridPane.add(guestLastNameField, 1,1);

        Label guestAgeLabel = new Label("Wiek:");
        TextField guestAgeField = new TextField();

        guestAgeField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){
                guestAgeField.setText(oldValue);
            }
        });

        gridPane.add(guestAgeLabel, 0, 2);
        gridPane.add(guestAgeField, 1,2);

        Label guestGenderLabel = new Label ("Płeć");
        ComboBox<String> genderTypeField = new ComboBox<>();
        genderTypeField.getItems().addAll(Properties.MALE, Properties.FEMALE);
        genderTypeField.setValue(Properties.FEMALE);

        gridPane.add(guestGenderLabel, 0 , 3);
        gridPane.add(genderTypeField, 1,3);

        Button addNewGuestButton = new Button("Dodaj nowego gościa");
        addNewGuestButton.setOnAction(actionEvent -> {
            int guestAge = Integer.parseInt(guestAgeField.getText());
            String firstName = guestFirstNameField.getText();
            String lastName = guestLastNameField.getText();
            String gender = genderTypeField.getValue();

            boolean isMale = false;

            if (gender.equals(Properties.MALE)){
                isMale = true;
            }

            this.guestService.createNewGuest(firstName,lastName,guestAge,isMale);

            guestTableView.getItems().clear();

            guestTableView.getItems().addAll(this.guestService.getGuestsAsDTO());

            modalStage.close();

        });

        gridPane.add(addNewGuestButton, 0,4);

        this.mainScene = new Scene(gridPane, 640, 480);
        this.mainScene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());

    }


    public Scene getMainScene() {
        return this.mainScene;
    }
}
