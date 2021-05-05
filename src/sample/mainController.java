package sample;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;


public class mainController {

    @FXML
    private Pane paneTest;
    @FXML
    private Pane monComptePanel;
    @FXML
    private JFXTextField textefieldPrenomMonCompte;
    @FXML
    private JFXTextField textefieldNomMonCompte;
    @FXML
    private JFXListView<Client> ListeClientsPanelClients;
    @FXML
    private Label dateJour;

    @FXML
    private JFXTextField textefieldMailMonCompte;

    @FXML
    private JFXTextField textefieldVilleMonCompte;

    @FXML
    private JFXButton boutonValiderMonCompte;
    @FXML
    private Pane rdvPannel;
    @FXML
    private Pane clientsPanel;
    @FXML
    private Pane planningPanel;
    @FXML
    private Pane cell00,cell01,cell02,cell03,cell04,cell10,cell11,cell12,cell13,cell14,cell20,cell21,cell22,cell23,cell24,cell30,cell31,cell32,cell33,cell34;
    @FXML
    private Label labelrdv00, labelrdv01,labelrdv02,labelrdv03,labelrdv04,labelrdv10,labelrdv11,labelrdv12,labelrdv13,labelrdv14,labelrdv20,labelrdv21,labelrdv22,labelrdv23,labelrdv24,labelrdv30,labelrdv31,labelrdv32,labelrdv33,labelrdv34;

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
    boolean test=false;





    @FXML
    public void initialisation(int id) throws SQLException {

        String dateFrancaise=LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy",Locale.FRENCH));
        dateJour.setText(dateFrancaise);
        labelNumeroSemaineDynamique.setText("gfsf");
        affichagePanel(true,false,false,false,false);
        activeSession = new Commercial(bdd.getPrenomCommercial(id), bdd.getNomCommercial(id),bdd.getVilleCommercial(id),bdd.getMailCommercial(id), id);
        activeSession.setListeClients(bdd.getListeClients(id));
        labelBienvenueDynamique.setText(activeSession.getPrenom());
        btnAddClients.setCursor(Cursor.HAND);
        btnClients.setCursor(Cursor.HAND);
        btnRdv.setCursor(Cursor.HAND);
        btnPlanningSemaine.setCursor(Cursor.HAND);
        setNumeroSemaine(getSemaineActuelle());
        labelNumeroSemaineDynamique.setText(""+numeroSemaine);
        datesSemaineIntilialisation(numeroSemaine);
        activeSession.setListeRdv(bdd.getListeRdv(id));
        rdvSemaineInitialisation();


    }



    public void checkRdv(){


    }




    public void rdvSemaineInitialisation(){
        final long calendarWeek = numeroSemaine;
        System.out.println(numeroSemaine);
        cell00.setOnMouseClicked(null);
        for (rdv rdv : activeSession.getListeRdv()){
            LocalDate date =  rdv.getDate().toLocalDate();
            LocalDate desiredDate = LocalDate.now().with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            if(date.equals(desiredDate)){

                if(rdv.getCreneau()==1){
                    cell00.setStyle("-fx-background-color: #46c646;");
                    labelrdv00.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                        cell00.setOnMouseClicked((MouseEvent e) -> {

                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/rdvResume.fxml"));
                                Parent root1 = (Parent) fxmlLoader.load();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root1));
                                stage.show();
                                ((rdvResumeController) fxmlLoader.getController()).getRdvResumeDate().setText(desiredDate.toString());
                                ((rdvResumeController) fxmlLoader.getController()).getRdvResumePrenom().setText(bdd.getPrenomClient(rdv.getIdClient()) + " " + bdd.getNomClient(rdv.getIdClient()));
                                ((rdvResumeController) fxmlLoader.getController()).getRdvResumeSociete().setText((bdd.getRaisonSociale(rdv.getIdClient())));
                                ((rdvResumeController) fxmlLoader.getController()).getTextAreaCommentaire().setText((rdv.getCommentaire()));


                            } catch (Exception a) {
                                System.out.println(a.getMessage());
                            }

                        });




                }



                if(rdv.getCreneau()==2){
                    cell10.setStyle("-fx-background-color: #46c646;");
                    labelrdv10.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell10.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/rdvResume.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root1));
                            stage.show();
                            ((rdvResumeController) fxmlLoader.getController()).getRdvResumeDate().setText(desiredDate.toString());
                            ((rdvResumeController) fxmlLoader.getController()).getRdvResumePrenom().setText(bdd.getPrenomClient(rdv.getIdClient()) + " " + bdd.getNomClient(rdv.getIdClient()));
                            ((rdvResumeController) fxmlLoader.getController()).getRdvResumeSociete().setText((bdd.getRaisonSociale(rdv.getIdClient())));
                            ((rdvResumeController) fxmlLoader.getController()).getTextAreaCommentaire().setText((rdv.getCommentaire()));


                        } catch (Exception a) {
                            System.out.println(a.getMessage());
                        }

                    });
                }
                if(rdv.getCreneau()==3){
                    cell20.setStyle("-fx-background-color: #46c646;");
                    labelrdv20.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));
                    cell20.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/rdvResume.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root1));
                            stage.show();
                            ((rdvResumeController) fxmlLoader.getController()).getRdvResumeDate().setText(desiredDate.toString());
                            ((rdvResumeController) fxmlLoader.getController()).getRdvResumePrenom().setText(bdd.getPrenomClient(rdv.getIdClient()) + " " + bdd.getNomClient(rdv.getIdClient()));
                            ((rdvResumeController) fxmlLoader.getController()).getRdvResumeSociete().setText((bdd.getRaisonSociale(rdv.getIdClient())));
                            ((rdvResumeController) fxmlLoader.getController()).getTextAreaCommentaire().setText((rdv.getCommentaire()));


                        } catch (Exception a) {
                            System.out.println(a.getMessage());
                        }

                    });
                }
                if(rdv.getCreneau()==4){
                    cell30.setStyle("-fx-background-color: #46c646;");
                    labelrdv30.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));
                    cell00.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/rdvResume.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root1));
                            stage.show();
                            ((rdvResumeController) fxmlLoader.getController()).getRdvResumeDate().setText(desiredDate.toString());
                            ((rdvResumeController) fxmlLoader.getController()).getRdvResumePrenom().setText(bdd.getPrenomClient(rdv.getIdClient()) + " " + bdd.getNomClient(rdv.getIdClient()));
                            ((rdvResumeController) fxmlLoader.getController()).getRdvResumeSociete().setText((bdd.getRaisonSociale(rdv.getIdClient())));
                            ((rdvResumeController) fxmlLoader.getController()).getTextAreaCommentaire().setText((rdv.getCommentaire()));


                        } catch (Exception a) {
                            System.out.println(a.getMessage());
                        }

                    });
                }

            }

            if(date.equals(desiredDate.plusDays(1))){
                if(rdv.getCreneau()==1){
                    System.out.println("dfsfqfqsdsddddddddddddddddddddd");
                    cell01.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==2){
                    cell11.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==3){
                    cell21.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==4){
                    cell31.setStyle("-fx-background-color: #46c646;");
                }

            }

            if(date.equals(desiredDate.plusDays(2))){
                if(rdv.getCreneau()==1){
                    cell02.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==2){
                    cell12.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==3){
                    cell22.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==4){
                    cell32.setStyle("-fx-background-color: #46c646;");
                }

            }

            if(date.equals(desiredDate.plusDays(3))){
                if(rdv.getCreneau()==1){
                    cell03.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==2){
                    cell13.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==3){
                    cell23.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==4){
                    cell33.setStyle("-fx-background-color: #46c646;");
                }

            }

            if(date.equals(desiredDate.plusDays(4))){
                if(rdv.getCreneau()==1){
                    cell04.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==2){
                    cell14.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==3){
                    cell24.setStyle("-fx-background-color: #46c646;");
                }
                if(rdv.getCreneau()==4){
                    cell34.setStyle("-fx-background-color: #46c646;");
                }

            }

        }

    }




    @FXML
    void closeButtonAction(ActionEvent event){
        exit();
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



        bdd.createClient(activeSession.getId(),textFieldNom,textFieldPrenom,textFieldRaisonSociale,textFieldMail,textFieldNumtel);
        Client client = new Client(textFieldPrenom.getText(),textFieldPrenom.getText(), textFieldNom.getText(), textFieldMail.getText(),textFieldNumtel.getAnchor(),bdd.getIdLastClient());
        activeSession.getListeClients().add(client);
        textFieldNom.clear();
        textFieldPrenom.clear();
        textFieldRaisonSociale.clear();
        textFieldMail.clear();
        textFieldNumtel.clear();

    }



    @FXML
    void btnMonComptePressed(ActionEvent event) {

        affichagePanel(false,false,false,false,true);
        textefieldPrenomMonCompte.setPromptText(bdd.getPrenomCommercial(activeSession.getId()));
        textefieldNomMonCompte.setPromptText(bdd.getNomCommercial(activeSession.getId()));
        textefieldMailMonCompte.setPromptText(bdd.getMailCommercial(activeSession.getId()));
        textefieldVilleMonCompte.setPromptText(bdd.getVilleCommercial(activeSession.getId()));

    }



    @FXML
    void boutonValiderMonComptePressed(ActionEvent event) {
        if(!textefieldPrenomMonCompte.getText().isEmpty()){
            bdd.setPrenomCommercial(activeSession.getId(),textefieldPrenomMonCompte.getText());
        }

        if(!textefieldNomMonCompte.getText().isEmpty()){
            bdd.setNomCommercial(activeSession.getId(),textefieldNomMonCompte.getText());
        }

        if(!textefieldMailMonCompte.getText().isEmpty()){
            bdd.setEmailCommercial(activeSession.getId(),textefieldMailMonCompte.getText());
        }

        if(!textefieldVilleMonCompte.getText().isEmpty()){
            bdd.setVilleCommercial(activeSession.getId(),textefieldVilleMonCompte.getText());
        }

        textefieldPrenomMonCompte.clear();
        textefieldNomMonCompte.clear();
        textefieldMailMonCompte.clear();
        textefieldVilleMonCompte.clear();
        textefieldPrenomMonCompte.setPromptText(bdd.getPrenomCommercial(activeSession.getId()));
        textefieldNomMonCompte.setPromptText(bdd.getNomCommercial(activeSession.getId()));
        textefieldMailMonCompte.setPromptText(bdd.getMailCommercial(activeSession.getId()));
        textefieldVilleMonCompte.setPromptText(bdd.getVilleCommercial(activeSession.getId()));

    }



    @FXML
    void btnClientsPressed(ActionEvent event) throws SQLException {

        affichagePanel(false,false,true,false,false);
        ListeClientsPanelClients.getItems().clear();
        activeSession.setListeClients(bdd.getListeClients(activeSession.getId()));


        for (Client client : activeSession.getListeClients()){

            ListeClientsPanelClients.getItems().add(client);

        }

    }



    @FXML
    void btnAddClientsPressed(ActionEvent event) {
        affichagePanel(false,false,false,true,false);
        textFieldNom.clear();
        textFieldPrenom.clear();
        textFieldRaisonSociale.clear();
        textFieldMail.clear();
        textFieldNumtel.clear();
    }





    @FXML
    void btnPlanningSemainePressed(ActionEvent event) {
        affichagePanel(true,false,false,false,false);
        rdvSemaineInitialisation();
    }





    @FXML
    void btnRdvPressed(ActionEvent event) {
        affichagePanel(false,true,false,false,false);
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
            clearCells();
            rdvSemaineInitialisation();
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
            clearCells();
            rdvSemaineInitialisation();
        }
        else{
            setNumeroSemaine(1);
            datesSemaineIntilialisation(numeroSemaine);
            labelNumeroSemaineDynamique.setText("" + numeroSemaine);
        }
    }





    void affichagePanel (boolean planning, boolean rdv,boolean clients, boolean addClient, boolean monCompte ){

        rdvPannel.setVisible(rdv);
        clientsPanel.setVisible(clients);
        planningPanel.setVisible(planning);
        addClientsPanel.setVisible(addClient);
        monComptePanel.setVisible(monCompte);

    }





    @FXML
    void addRdvPressed(ActionEvent event) throws SQLException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/rdv.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            ((rdvController)fxmlLoader.getController()).setActiveSession(this.activeSession);
            ((rdvController)fxmlLoader.getController()).addRdvInit();


        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    void clearCells(){


        cell00.setStyle("-fx-background-color:  #D1D1D1;");
        cell01.setStyle("-fx-background-color:  #D1D1D1;");
        cell02.setStyle("-fx-background-color:  #D1D1D1;");
        cell03.setStyle("-fx-background-color:  #D1D1D1;");
        cell04.setStyle("-fx-background-color:  #D1D1D1;");
        cell10.setStyle("-fx-background-color:  #D1D1D1;");
        cell11.setStyle("-fx-background-color:  #D1D1D1;");
        cell12.setStyle("-fx-background-color:  #D1D1D1;");
        cell13.setStyle("-fx-background-color:  #D1D1D1;");
        cell14.setStyle("-fx-background-color:  #D1D1D1;");
        cell20.setStyle("-fx-background-color:  #D1D1D1;");
        cell21.setStyle("-fx-background-color:  #D1D1D1;");
        cell22.setStyle("-fx-background-color:  #D1D1D1;");
        cell23.setStyle("-fx-background-color:  #D1D1D1;");
        cell24.setStyle("-fx-background-color:  #D1D1D1;");
        cell30.setStyle("-fx-background-color:  #D1D1D1;");
        cell31.setStyle("-fx-background-color:  #D1D1D1;");
        cell32.setStyle("-fx-background-color:  #D1D1D1;");
        cell33.setStyle("-fx-background-color:  #D1D1D1;");
        cell34.setStyle("-fx-background-color:  #D1D1D1;");
        labelrdv00.setText("");
        labelrdv01.setText("");
        labelrdv02.setText("");
        labelrdv03.setText("");
        labelrdv04.setText("");
        labelrdv10.setText("");
        labelrdv11.setText("");
        labelrdv12.setText("");
        labelrdv13.setText("");
        labelrdv14.setText("");
        labelrdv20.setText("");
        labelrdv21.setText("");
        labelrdv22.setText("");
        labelrdv23.setText("");
        labelrdv24.setText("");
        labelrdv30.setText("");
        labelrdv31.setText("");
        labelrdv32.setText("");
        labelrdv33.setText("");
        labelrdv34.setText("");

        cell00.setOnMouseClicked(null);
        cell01.setOnMouseClicked(null);
        cell02.setOnMouseClicked(null);
        cell03.setOnMouseClicked(null);
        cell04.setOnMouseClicked(null);
        cell10.setOnMouseClicked(null);
        cell11.setOnMouseClicked(null);
        cell12.setOnMouseClicked(null);
        cell13.setOnMouseClicked(null);
        cell14.setOnMouseClicked(null);
        cell20.setOnMouseClicked(null);
        cell21.setOnMouseClicked(null);
        cell22.setOnMouseClicked(null);
        cell23.setOnMouseClicked(null);
        cell24.setOnMouseClicked(null);
        cell30.setOnMouseClicked(null);
        cell31.setOnMouseClicked(null);
        cell32.setOnMouseClicked(null);
        cell33.setOnMouseClicked(null);
        cell34.setOnMouseClicked(null);






    }



    public LocalDate getDateActuelle(){
        return java.time.LocalDate.now();
    }




    public int getSemaineActuelle(){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = getDateActuelle().get(weekFields.weekOfWeekBasedYear());
        return weekNumber;
    }




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









}
