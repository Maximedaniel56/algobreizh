package sample;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class loginController extends mainController{

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
    private AnchorPane fondPrincipal;

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
    public void final_btn_connexion_pressed(ActionEvent event) {
        if(bdd.login(login,password)==0){
            System.out.println("Mauvais login/mot de passe");
        }
        else if (bdd.login(login,password)>0){

            System.out.println("connecté");
            btn_log.setText("connecté");
            btn_log.setStyle("-fx-background-color: green ; -fx-background-radius: 10em;");

            FadeTransition ft = new FadeTransition(Duration.millis(1000), fondPrincipal);
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setOnFinished(e -> exit());
            ft.play();


            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/main.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root1);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
                System.out.println("gdfg");
                ((mainController)fxmlLoader.getController()).initialisation();





            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }


    }
    @FXML
     void closeButtonAction(ActionEvent event){
        exit();
    }


    private void exit(){

        Stage primaryStage = (Stage) btn_exit.getScene().getWindow();
        primaryStage.close();
    }
    private void setTranslate(AnchorPane pane, int duration, int value){

        TranslateTransition rt = new TranslateTransition(Duration.millis(duration), pane);

        rt.setByX(value);
        rt.play();



    }









}
