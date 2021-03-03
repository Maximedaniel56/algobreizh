package sample;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Controller {

     boolean inscription_panel_deployed =false;
     boolean connect_panel_deployed =false;

    @FXML private TextField inscription_identifiant;
    @FXML private TextField inscription_email;
    @FXML private PasswordField inscription_mdp;
    @FXML private PasswordField inscription_mdp2;
    @FXML private PasswordField password;
    @FXML private TextField login;



    public TextField getInscription_identifiant() {
        return inscription_identifiant;
    }

    public void setInscription_identifiant(TextField inscription_identifiant) {
        this.inscription_identifiant = inscription_identifiant;
    }

    @FXML
    private AnchorPane fond_blanc_connect;

    @FXML
    private JFXButton btn_log;

    @FXML
    private AnchorPane fond_blanc_inscription;

    @FXML
    private AnchorPane fond_bleu;

    @FXML
    private JFXButton btn_connect;

    @FXML
    private JFXButton btn_inscript;

    @FXML
    private Button btn_exit;

    @FXML
    private Button btn_rdc;



    @FXML
    void btn_connect_pressed(ActionEvent event) throws InterruptedException {


        if(inscription_panel_deployed==true){

            setTranslate(fond_blanc_inscription,500,-270);
            inscription_panel_deployed =false;

        }


        if (connect_panel_deployed ==false){

            setTranslate(fond_blanc_connect,500,270);
            connect_panel_deployed =true;

        }

        else {

            setTranslate(fond_blanc_connect,500,-270);
            connect_panel_deployed =false;

        }

    }
    @FXML
    void btn_inscription_pressed(ActionEvent event) {

        if(connect_panel_deployed==true){

            setTranslate(fond_blanc_connect,500,-270);
            connect_panel_deployed =false;

        }

        if (inscription_panel_deployed ==false){

            setTranslate(fond_blanc_inscription,500,270);
            inscription_panel_deployed =true;

        }

        else {

            setTranslate(fond_blanc_inscription,500,-270);
            inscription_panel_deployed =false;

        }



    }


    @FXML
    void btn_exit_pressed(ActionEvent event) {


    }

    @FXML
    void final_btn_inscription_pressed(ActionEvent event) {
        bdd.createAccount(inscription_identifiant, inscription_email, inscription_mdp);

    }

    @FXML
    void final_btn_connexion_pressed(ActionEvent event) throws SQLException {
        bdd.login(login,password);

    }
    @FXML
     void closeButtonAction(ActionEvent event){
        // get a handle to the stage
        Stage primaryStage = (Stage) btn_exit.getScene().getWindow();
        // do what you have to do
        primaryStage.close();
    }

    private void setTranslate(AnchorPane pane, int duration, int value){

        TranslateTransition rt = new TranslateTransition(Duration.millis(duration), pane);

        rt.setByX(value);
        rt.play();



    }

}
