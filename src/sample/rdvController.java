package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;

import java.sql.Date;
import java.sql.SQLException;
import java.time.ZoneId;

public class rdvController {

    @FXML
    private Pane paneTest;
    @FXML
    private ComboBox<String> selecteurClients;
    @FXML
    private DatePicker datePicker;



    @FXML
    void addRdvInit() throws SQLException {


        paneTest.setStyle("-fx-background-color:  #650D73;");
        selecteurClients.getItems().addAll();


    }

    @FXML
    void boutonValiderAjoutRdvPressed(ActionEvent event){

       /* ObservableList<String> observableList = FXCollections.observableList();*/


        paneTest.setStyle("-fx-background-color:  #200D73;");

/*
        ZoneId defaultZoneId = ZoneId.systemDefault();

        System.out.println(datePicker.getValue());
        Date date = (Date) Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());

        Client test = new Client("carrefour", "paul", "albert","fdsfd@carrefour.fr",06054);
        bdd.createRendezVous(date,test,1 );
*/
    }

}
