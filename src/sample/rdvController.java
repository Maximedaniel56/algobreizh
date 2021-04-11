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


    private Commercial activeSession;
    @FXML
    private Pane paneTest;
    @FXML
    private ComboBox<Client> selecteurClients;
    @FXML
    private DatePicker datePicker;

    public Commercial getActiveSession() {
        return activeSession;
    }

    public void setActiveSession(Commercial activeSession) {
        this.activeSession = activeSession;
    }

    @FXML
    void addRdvInit() throws SQLException {


        selecteurClients.getItems().addAll(activeSession.getListeClients().get(0));


    }

    @FXML
    void boutonValiderAjoutRdvPressed(ActionEvent event){

       /* ObservableList<String> observableList = FXCollections.observableList();*/



/*
        ZoneId defaultZoneId = ZoneId.systemDefault();

        System.out.println(datePicker.getValue());
        Date date = (Date) Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());

        Client test = new Client("carrefour", "paul", "albert","fdsfd@carrefour.fr",06054);
        bdd.createRendezVous(date,test,1 );
*/
    }

}
