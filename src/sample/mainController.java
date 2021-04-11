package sample;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;


public class mainController {

    @FXML
    private Pane paneTest;

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
    private Button btn_exitMain;
    @FXML
    private ComboBox<?> selecteurCreneau;



    @FXML
    private JFXButton boutonValiderAjoutRdv;

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
    private Label labelNumeroSemaineDynamique;
    @FXML
    private Label lundiDate;
    @FXML
    private Label mardiDate;
    @FXML
    private Label mercrediDate;
    @FXML
    private Label jeudiDate;
    @FXML
    private Label vendrediDate;
    private int numeroSemaine;
    private Commercial activeSession;

    public int getNumeroSemaine() {
        return numeroSemaine;
    }

    public void setNumeroSemaine(int numeroSemaine) {
        this.numeroSemaine = numeroSemaine;
    }

    public Commercial getActiveSession() {
        return activeSession;
    }

    public void setActiveSession(Commercial activeSession) {
        this.activeSession = activeSession;
    }

    @FXML
    public void initialisation() throws SQLException {
        labelNumeroSemaineDynamique.setText("gfsf");
        rdvPannel.setVisible(false);
        clientsPanel.setVisible(false);
        planningPanel.setVisible(true);
        activeSession = new Commercial("Paul", "albert","fdsfd@carrefour.fr",06054);
        activeSession.setPrenom("Julien");
        labelBienvenueDynamique.setText(activeSession.getPrenom());
        btnAddClients.setCursor(Cursor.HAND);
        setNumeroSemaine(getSemaineActuelle());
        labelNumeroSemaineDynamique.setText(""+numeroSemaine);
        datesSemaineIntilialisation(numeroSemaine);


    }

    public void datesSemaineIntilialisation(int week){

        final long calendarWeek = week;
        LocalDate desiredDate = LocalDate.now()
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        lundiDate.setText(""+desiredDate);
        mardiDate.setText(""+desiredDate.plusDays(1));
        mercrediDate.setText(""+desiredDate.plusDays(2));
        jeudiDate.setText(""+desiredDate.plusDays(3));
        vendrediDate.setText(""+desiredDate.plusDays(4));



    }


    @FXML
    void BoutonValiderAddClient_pressed(ActionEvent event) {

        Client client = new Client(textFieldPrenom.getText(),textFieldPrenom.getText(), textFieldNom.getText(), textFieldMail.getText(),textFieldNumtel.getAnchor());

        bdd.createClient(textFieldNom,textFieldPrenom,textFieldRaisonSociale,textFieldMail,textFieldNumtel);
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
    void flecheDecrementerPressed(MouseEvent event) {

        if (numeroSemaine>1) {
            datesSemaineIntilialisation(numeroSemaine - 1);
            numeroSemaine = numeroSemaine - 1;
            labelNumeroSemaineDynamique.setText("" + numeroSemaine);
        }
        else{
            setNumeroSemaine(52);
            datesSemaineIntilialisation(numeroSemaine);
            labelNumeroSemaineDynamique.setText("" + numeroSemaine);

        }



    }

    @FXML
    void flecheIncrementerPressed(MouseEvent event) {


        if (numeroSemaine<52) {
            datesSemaineIntilialisation(numeroSemaine + 1);
            numeroSemaine = numeroSemaine + 1;
            labelNumeroSemaineDynamique.setText("" + numeroSemaine);
        }
        else{
            setNumeroSemaine(1);
            datesSemaineIntilialisation(numeroSemaine);
            labelNumeroSemaineDynamique.setText("" + numeroSemaine);

        }
    }



    @FXML
    void addRdvPressed(ActionEvent event) throws SQLException {

        cell00.setStyle("-fx-background-color:  #650D73;");





        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/rdv.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            ((rdvController)fxmlLoader.getController()).addRdvInit();









        } catch (Exception e){
            System.out.println(e.getMessage());
        }



    }

    public LocalDate getDateActuelle(){
        return java.time.LocalDate.now();
    }

    public int getSemaineActuelle(){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = getDateActuelle().get(weekFields.weekOfWeekBasedYear());
        return weekNumber;
    }
}
