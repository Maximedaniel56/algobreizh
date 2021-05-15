package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.ZoneId;


public class rdvController implements myFXController{


    private Commercial activeSession;
    @FXML
    private Pane paneTest;
    @FXML
    private ComboBox<Client> selecteurClients;
    @FXML
    private ComboBox<String> selecteurCreneau;
    @FXML
    private Label labelErreurRdv;
    @FXML
    private DatePicker datePicker;
    @FXML
    private JFXButton boutonValiderAjoutRdv;
    @FXML
    private JFXTextArea textAreaAddRdv;

    private int creneau;


    public Commercial getActiveSession() {
        return activeSession;
    }

    public void setActiveSession(Commercial activeSession) {
        this.activeSession = activeSession;
    }

    @FXML
    void addRdvInit() throws SQLException {


        activeSession.setListeClients(bdd.getListeClients(activeSession.getId()));
        labelErreurRdv.setAlignment(Pos.CENTER);



        for (Client client : activeSession.getListeClients()){

            selecteurClients.getItems().add(client);

        }

        selecteurCreneau.getItems().add("Matin 8h-10h");
        selecteurCreneau.getItems().add("Matin 10h-12h");
        selecteurCreneau.getItems().add("Après-midi 13h-15h");
        selecteurCreneau.getItems().add("Après-midi 15h-17h");

    }

    @FXML
    private void exit(){

        Stage stage = (Stage) boutonValiderAjoutRdv.getScene().getWindow();
        stage.close();
    }





    @FXML
    void boutonValiderAjoutRdvPressed(ActionEvent event) throws SQLException {




        if(datePicker.getValue()!=null){
            Date date = Date.valueOf(datePicker.getValue());
            if(selecteurClients.getSelectionModel().isEmpty()==false){
                if(selecteurCreneau.getSelectionModel().isEmpty()==false){

                    if(selecteurCreneau.getValue().equals("Matin 8h-10h")){

                        creneau=1;
                    }

                    if(selecteurCreneau.getValue().equals("Matin 10h-12h")){

                        creneau=2;
                    }

                    if(selecteurCreneau.getValue().equals("Après-midi 13h-15h")){

                        creneau=3;
                    }

                    if(selecteurCreneau.getValue().equals("Après-midi 15h-17h")){

                        creneau=4;
                    }

                    if (textAreaAddRdv.getText().isEmpty()){textAreaAddRdv.setText("NULL");}


                    if (bdd.checkDateDispo(date,creneau,activeSession.getId())) {

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("/fxml/main.fxml"));
                        System.out.println(new rdv(date, creneau, selecteurClients.getValue().getId(), textAreaAddRdv.getText()));
                        activeSession.getListeRdv().add(new rdv(date, creneau, selecteurClients.getValue().getId(), textAreaAddRdv.getText()));
                        bdd.createRendezVous(selecteurClients.getValue().getId(), date, creneau, activeSession.getId(), textAreaAddRdv.getText());
                        exit();
                    }

                    else{
                        labelErreurRdv.setText("créneau indisponible");

                    }



                }else{
                    labelErreurRdv.setText("veuillez choisir un créneau");
                }
            }
            else{
                labelErreurRdv.setText("veuillez choisir un client");
            }
        }
        else{
            labelErreurRdv.setText("veuillez choisir une date");
        }

        //




    }

    @Override
    public Commercial getReturn() {
        return activeSession;
    }
}
