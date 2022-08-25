package pl.clockworkjava.ui.gui.rooms;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.clockworkjava.domain.ObjectPool;
import pl.clockworkjava.domain.room.RoomService;
import pl.clockworkjava.domain.room.dto.RoomDTO;


import java.util.List;
public class RoomsTab {
    private Tab roomTab;
    private RoomService roomService = ObjectPool.getRoomService();
    private Stage primaryStage;
    public RoomsTab(Stage primaryStage) {
        TableView<RoomDTO> tableView = getRoomDTOTableView();
        this.primaryStage = primaryStage;
        Button button = new Button("Stwórz nowy");

        button.setOnAction(actionEvent -> {
            Stage addRoomPopup = new Stage();
            addRoomPopup.initModality(Modality.WINDOW_MODAL);

            addRoomPopup.setScene(new AddNewRoomScene(addRoomPopup, tableView).getMainScene());
            addRoomPopup.initOwner(this.primaryStage);
            addRoomPopup.setTitle("Dodawanie nowego pokoju");
            addRoomPopup.showAndWait();
        });

        VBox layout = new VBox(button, tableView);

        this.roomTab = new Tab("Pokoje", layout);
        this.roomTab.setClosable(false);
    }

    private TableView<RoomDTO> getRoomDTOTableView() {
        TableView<RoomDTO> tableView = new TableView<>();

        TableColumn<RoomDTO, Integer> numberColumn = new TableColumn<>("Numer");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<RoomDTO, String> bedsColumn = new TableColumn<>("Typy łóżek");
        bedsColumn.setCellValueFactory(new PropertyValueFactory<>("beds"));

        TableColumn<RoomDTO, Integer> bedsCountColumn = new TableColumn<>("Ilość łóżek") ;
        bedsCountColumn.setCellValueFactory(new PropertyValueFactory<>("bedsCount"));

        TableColumn<RoomDTO, Integer> roomSizeColumn = new TableColumn<>("Rozmiar pokoju") ;
        roomSizeColumn.setCellValueFactory(new PropertyValueFactory<>("roomSize"));

        TableColumn <RoomDTO, RoomDTO> deleteColumn = new TableColumn<>("Usuń");
        deleteColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper(value.getValue()));

        deleteColumn.setCellFactory( param-> new TableCell<>(){

            Button deleteButton = new Button("Usuń");
            Button editButton = new Button ("Edytuj");
            HBox hbox = new HBox(deleteButton, editButton);


            @Override
            protected void updateItem(RoomDTO value, boolean empty){
                super.updateItem(value, empty);

                if (value == null){
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                    deleteButton.setOnAction(actionEvent -> {
                        roomService.removeRoom(value.getId());
                        tableView.getItems().remove(value);
                    });
                    editButton.setOnAction(actionEvent -> {
                        Stage addRoomPopup = new Stage();
                        addRoomPopup.initModality(Modality.WINDOW_MODAL);

                        addRoomPopup.setScene(new EditRoomScene(addRoomPopup, tableView, value).getMainScene());
                        addRoomPopup.initOwner(primaryStage);
                        addRoomPopup.setTitle("Dodawanie nowego pokoju");
                        addRoomPopup.showAndWait();
                    });
                }
            }
                });

        tableView.getColumns().addAll(numberColumn, roomSizeColumn,
                bedsCountColumn, bedsColumn, deleteColumn);

        List<RoomDTO> allAsDTO = roomService.getAllAsDTO();

        tableView.getItems().addAll(allAsDTO);
        return tableView;
    }

    public Tab getRoomTab() {
        return roomTab;
    }
}