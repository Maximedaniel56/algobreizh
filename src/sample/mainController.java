package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.layout.Pane;


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














    public void initialisation(){
        rdvPannel.setVisible(false);
        clientsPanel.setVisible(false);
        planningPanel.setVisible(true);

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

    }



}
