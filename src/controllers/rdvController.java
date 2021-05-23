package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.client;
import modele.commercial;
import modele.bdd;
import modele.rdv;

import java.sql.Date;
import java.sql.SQLException;


public class rdvController implements myFXController{


    private commercial activeSession;
    @FXML
    private Pane paneTest;
    @FXML
    private ComboBox<client> selecteurClients;
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


    public commercial getActiveSession() {
        return activeSession;
    }

    public void setActiveSession(commercial activeSession) {
        this.activeSession = activeSession;
    }

    /**
     * Initialise certaines données de la nouvelle fenêtre permettant d'ajouter un rdv :
     * Initialise la liste des clients et le label affichant les erreurs
     * @throws SQLException
     */

    @FXML
    void addRdvInit() throws SQLException {


        activeSession.setListeClients(bdd.getListeClients(activeSession.getId()));
        labelErreurRdv.setAlignment(Pos.CENTER);



        for (modele.client client : activeSession.getListeClients()){

            selecteurClients.getItems().add(client);

        }

        selecteurCreneau.getItems().add("Matin 8h-10h");
        selecteurCreneau.getItems().add("Matin 10h-12h");
        selecteurCreneau.getItems().add("Après-midi 13h-15h");
        selecteurCreneau.getItems().add("Après-midi 15h-17h");

    }

    /**
     * ferme la fenêtre
     */
    @FXML
    private void exit(){

        Stage stage = (Stage) boutonValiderAjoutRdv.getScene().getWindow();
        stage.close();
    }


    /**
     * Vérifie que le créneau est disponible, si oui le rdv est envoyé en bdd, dans l'objet commercial et la fenêtre fermée
     * @param event
     * @throws SQLException
     */
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
    public commercial getReturn() {
        return activeSession;
    }
}
