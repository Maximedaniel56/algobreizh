package sample;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
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
    private JFXComboBox<?> selecteurClient;
    @FXML
    private Button btn_exitMain;
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
    @FXML
    private Button btnAddClients;
    @FXML
    private Label labelBienvenueFixe;
    @FXML
    private Label labelBienvenueDynamique;
    @FXML
    private JFXButton BoutonValiderAddClient;
    @FXML
    private JFXTextField textFieldPrenom;
    @FXML
    private Pane addClientsPanel;
    @FXML
    private JFXTextField textFieldNom;

    @FXML
    private JFXTextField textFieldRaisonSociale;

    @FXML
    private JFXTextField textFieldMail;

    @FXML
    private JFXTextField textFieldNumtel;





    @FXML
    public void initialisation(){
        rdvPannel.setVisible(false);
        clientsPanel.setVisible(false);
        planningPanel.setVisible(true);
        Commercial commercial = new Commercial("Paul", "albert","fdsfd@carrefour.fr",06054);
        commercial.setPrenom("Julien");
        labelBienvenueDynamique.setText(commercial.getPrenom());
        btnAddClients.setCursor(Cursor.HAND);





    }



    @FXML
    void mouseOver (ActionEvent event){
        btnClients.setText("test");
    }

    @FXML
    void BoutonValiderAddClient_pressed(ActionEvent event) {

        Client client = new Client(textFieldPrenom.getText(),textFieldPrenom.getText(), textFieldNom.getText(), textFieldMail.getText(),textFieldNumtel.getAnchor());

        bdd.createClient(textFieldNom,textFieldPrenom,textFieldRaisonSociale,textFieldMail,textFieldNumtel);
  }




    @FXML
    void boutonValiderAjoutRdvPressed(ActionEvent event){
        ZoneId defaultZoneId = ZoneId.systemDefault();

        System.out.println(datePicker.getValue());
        Date date = (Date) Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());

        Client test = new Client("carrefour", "paul", "albert","fdsfd@carrefour.fr",06054);
        bdd.createRendezVous(date,test,1 );

    }




    @FXML
    void btnClientsPressed(ActionEvent event) throws SQLException {

        System.out.println(bdd.getListeClients());
        rdvPannel.setVisible(false);
        clientsPanel.setVisible(false);
        planningPanel.setVisible(false);
        clientsPanel.setVisible(true);


    }

    @FXML
    void btnAddClientsPressed(ActionEvent event) {

        rdvPannel.setVisible(false);
        clientsPanel.setVisible(false);
        planningPanel.setVisible(false);
        addClientsPanel.setVisible(true);

    }
    @FXML
    void btnPlanningSemainePressed(ActionEvent event) {

        rdvPannel.setVisible(false);
        clientsPanel.setVisible(false);
        planningPanel.setVisible(true);
        addClientsPanel.setVisible(false);
    }

    @FXML
    void btnRdvPressed(ActionEvent event) {

        rdvPannel.setVisible(true);
        clientsPanel.setVisible(false);
        planningPanel.setVisible(false);
        addClientsPanel.setVisible(false);
    }

    @FXML
    void closeButtonAction(ActionEvent event){
        exit();
    }

    @FXML
    private void exit(){

        Stage stage = (Stage) btn_exitMain.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addRdvPressed(ActionEvent event){

        cell00.setStyle("-fx-background-color:  #420D73;");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/rdv.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            ((mainController)fxmlLoader.getController()).initialisation();





        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }



}
