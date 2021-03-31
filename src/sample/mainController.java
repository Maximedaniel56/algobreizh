package sample;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.ZoneId;



public class mainController {


    @FXML
    private Pane rdvPannel;
    @FXML
    private Pane clientsPanel;
    @FXML
    private Pane planningPanel;
    @FXML
    private Pane cell00, cell01,cell02,cell03,cell04,cell10,cell11,cell12,cell13,cell14,cell20,cell21,cell22,cell23,cell24,cell30,cell31,cell32,cell33,cell34;
    @FXML
    private Button btnPlanningSemaine;
    @FXML
    private Button btnRdv;
    @FXML
    private Button btnClients;
    @FXML
    private Button test;
    @FXML
    private JFXComboBox<?> selecteurClient;

    @FXML
    private JFXCheckBox creneau1;

    @FXML
    private JFXCheckBox creneau2;

    @FXML
    private JFXCheckBox creneau3;

    @FXML
    private JFXCheckBox creneau4;

    @FXML
    private JFXButton boutonValiderAjoutRdv;

    @FXML
    private DatePicker datePicker;








    public void initialisation(){
        rdvPannel.setVisible(false);
        clientsPanel.setVisible(false);
        planningPanel.setVisible(true);

    }

    @FXML
    void boutonValiderAjoutRdvPressed(ActionEvent event){
        ZoneId defaultZoneId = ZoneId.systemDefault();

        System.out.println("test");
        System.out.println(datePicker.getValue());
        Date date = (Date) Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());

        Client test = new Client("carrefour", "paul", "albert","fdsfd@carrefour.fr",06054);
        bdd.createRendezVous(date,test,1 );

    }





    @FXML
    void btnClientsPressed(ActionEvent event) {

    }

    @FXML
    void btnPlanningSemainePressed(ActionEvent event) {

    }

    @FXML
    void btnRdvPressed(ActionEvent event) {

    }



    @FXML
    void test_pressed(ActionEvent event){

        cell00.setStyle("-fx-background-color:  #420D73;");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/rdv.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            System.out.println("gdfg");
            ((mainController)fxmlLoader.getController()).initialisation();





        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }



}
