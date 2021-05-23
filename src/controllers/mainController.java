package controllers;


import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.*;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private JFXListView<client> ListeClientsPanelClients;
    @FXML
    private Label dateJour;

    @FXML
    private JFXTextField textefieldMailMonCompte;

    @FXML
    private JFXTextField textefieldVilleMonCompte;
    @FXML
    private Label labelInfoPrenom;

    @FXML
    private Label labelInfoNom;

    @FXML
    private Label labelInfoRaisonSociale;

    @FXML
    private Label labelInfoMail;

    @FXML
    private Label labelInfoTel;
    @FXML
    private Label labelErreurAddClient;


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
    @FXML
    private TableView<client> TableauClients;

    @FXML
    private TableColumn<client, String> tableauClients_ColonnePrenom;

    @FXML
    private TableColumn<client, String> tableauClients_ColonneNom;

    @FXML
    private TableColumn<client, String> tableauClients_ColonneRaisonSociale;

    @FXML
    private TableColumn<client, String> tableauClients_ColonneDernierRdv;
    private List<Pane> listeCellules;
    private List<Label> listeLabels;

    private int numeroSemaine;
    private commercial activeSession;
    boolean test=false;


    /**
     * Initialise un ensemble de données à la connexion :
     *  - Récupère la date du jour, le numéro de semaine courant et initialise ces données pour que le planning de la semaine s'affiche et soit rempli
     *  - Affiche le pannel "planning" par défaut à l'ouverture
     *  - Initialise l'objet Client en récupérant sur la bdd toutes les données qui lui sont associées  (dont sa liste de clients)
     *  - Initialise quelques détails d'ergonimie et d'interface
     *  - Initialise le tableau des clients à visiter
     *  - Initialise la liste des clients
     *
     *
     * @param id ID récupéré lors de la connection, correspondant à l'id du compte connecté
     * @throws SQLException
     */

    @FXML
    public void initialisation(int id) throws SQLException {

        String dateFrancaise=LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy",Locale.FRENCH));
        dateJour.setText(dateFrancaise);
        labelNumeroSemaineDynamique.setText("gfsf");
        affichagePanel(true,false,false,false,false);
        activeSession = new commercial(bdd.getPrenomCommercial(id), bdd.getNomCommercial(id),bdd.getVilleCommercial(id),bdd.getMailCommercial(id), id);
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
        initCells();
        initLabels();
        activeSession.setClientsPrioritaires(bdd.getListeClients(activeSession.getId()));
        initialiserListeClientsTries();
        initialiserTableauClients();


    }

    /**
     * Initialise la liste des clients triée par la date de dernière visite (fonctionne mais non utilisée)
     * @throws SQLException
     */
    public void initialiserListeClientsTries() throws SQLException {

        ArrayList<Integer> liste = new ArrayList();
        liste.addAll(bdd.getListeIdClientsTries(activeSession.getId()));
        activeSession.getClientsPrioritaires().clear();
        for (int i=0; i<liste.size(); i++) {

            for(modele.client client : activeSession.getListeClients()){
                if (liste.get(i)==client.getId()){
                    activeSession.getClientsPrioritaires().add(client);
                }
            }
        }


    }

    /**
     * Initialise les colonnes du tableau de clients dans l'onglet "visites recommandées"
     */

    public void initialiserTableauClients(){



        ObservableList<client> liste = FXCollections.observableArrayList(activeSession.getListeClients());

        tableauClients_ColonnePrenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));

        tableauClients_ColonneNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));

        tableauClients_ColonneRaisonSociale.setCellValueFactory(new PropertyValueFactory<>("raisonSociale"));

        tableauClients_ColonneDernierRdv.setCellValueFactory(new PropertyValueFactory<>("dernierRdv"));
        tableauClients_ColonneDernierRdv.setSortType(TableColumn.SortType.ASCENDING);

        TableauClients.setItems(liste);
        TableauClients.getSortOrder().add(tableauClients_ColonneDernierRdv);

    }


    /**
     * Initialise le planning de la semaine affiché
     * met en évidence chaque case correspondant à un rendez-vous et la rend cliquable.
     * Le clic sur la case (créneau) associée à un rdv ouvre une nouvelle fenêtre avec toutes les informations du rdv en question
     *
     */
    public void rdvSemaineInitialisation(){
        final long calendarWeek = numeroSemaine;
        cell00.setOnMouseClicked(null);
        for (modele.rdv rdv : activeSession.getListeRdv()){
            LocalDate date =  rdv.getDate().toLocalDate();
            LocalDate desiredDate = LocalDate.now().with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            if(date.equals(desiredDate)){

                if(rdv.getCreneau()==1){
                    cell00.setStyle("-fx-background-color: #46c646;");
                    labelrdv00.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                        cell00.setOnMouseClicked((MouseEvent e) -> {

                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("/fxml/rdvResume.fxml"));
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
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("/fxml/rdvResume.fxml"));
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
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("/fxml/rdvResume.fxml"));
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
                    cell01.setStyle("-fx-background-color: #46c646;");
                    labelrdv01.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell01.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("/fxml/rdvResume.fxml"));
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
                    cell11.setStyle("-fx-background-color: #46c646;");
                    labelrdv11.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell11.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell21.setStyle("-fx-background-color: #46c646;");
                    labelrdv21.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell21.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell31.setStyle("-fx-background-color: #46c646;");
                    labelrdv31.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell31.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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

            if(date.equals(desiredDate.plusDays(2))){
                if(rdv.getCreneau()==1){
                    cell02.setStyle("-fx-background-color: #46c646;");
                    labelrdv02.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell02.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell12.setStyle("-fx-background-color: #46c646;");
                    labelrdv12.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell12.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell22.setStyle("-fx-background-color: #46c646;");
                    labelrdv22.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell22.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell32.setStyle("-fx-background-color: #46c646;");
                    labelrdv32.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell32.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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

            if(date.equals(desiredDate.plusDays(3))){
                if(rdv.getCreneau()==1){
                    cell03.setStyle("-fx-background-color: #46c646;");
                    labelrdv03.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell03.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell13.setStyle("-fx-background-color: #46c646;");
                    labelrdv13.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell13.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell23.setStyle("-fx-background-color: #46c646;");
                    labelrdv23.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell23.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell33.setStyle("-fx-background-color: #46c646;");
                    labelrdv33.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell33.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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

            if(date.equals(desiredDate.plusDays(4))){
                if(rdv.getCreneau()==1){
                    cell04.setStyle("-fx-background-color: #46c646;");
                    labelrdv04.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell04.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell14.setStyle("-fx-background-color: #46c646;");
                    labelrdv14.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell14.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell24.setStyle("-fx-background-color: #46c646;");
                    labelrdv24.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell24.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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
                    cell34.setStyle("-fx-background-color: #46c646;");
                    labelrdv34.setText(""+bdd.getPrenomClient(rdv.getIdClient())+" "+bdd.getNomClient(rdv.getIdClient())+"\n"+bdd.getRaisonSociale(rdv.getIdClient()));

                    cell34.setOnMouseClicked((MouseEvent e) -> {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdvResume.fxml"));
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

        }

    }


    /**
     * Ferme Algobreizh
     * @param event
     */
    @FXML
    void closeButtonAction(ActionEvent event){
        exit();
    }



    /**
     * Actualise la date au dessus de chaque jour de la semaine en fonction du numéro de semaine
     * @param week
     */

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


    /**
     * Verifie les champs rentrés et crée un client, à la fois en local et à la fois sur la bdd
     * @param event
     * @throws SQLException
     */

    @FXML
    void BoutonValiderAddClient_pressed(ActionEvent event) throws SQLException {

        labelErreurAddClient.setAlignment(Pos.CENTER);
        if(textFieldPrenom.getText().isEmpty()){ labelErreurAddClient.setText("Veuillez entrer un prénom"); }
        else{
            if(textFieldNom.getText().isEmpty()){ labelErreurAddClient.setText("Veuillez entrer un Nom");}
            else{
                if(textFieldRaisonSociale.getText().isEmpty()){ labelErreurAddClient.setText("Veuillez entrer une raison sociale");}
                else{
                    if(textFieldNumtel.getLength()!=10){ labelErreurAddClient.setText("Le numéro de téléphone doit contenir 10 chiffres");}
                    else{
                        bdd.createClient(activeSession.getId(),textFieldPrenom,textFieldNom,textFieldRaisonSociale,textFieldMail,textFieldNumtel);
                        client client = new client(textFieldPrenom.getText(),textFieldPrenom.getText(), textFieldNom.getText(), textFieldMail.getText(),textFieldNumtel.getAnchor(),bdd.getIdLastClient());
                        activeSession.getListeClients().add(client);
                        initialiserListeClientsTries();
                        textFieldNom.clear();
                        textFieldPrenom.clear();
                        textFieldRaisonSociale.clear();
                        textFieldMail.clear();
                        textFieldNumtel.clear();
                        labelErreurAddClient.setTextFill(Color.GREEN);
                        labelErreurAddClient.setText("Client ajouté");
                    }
                }
            }
        }



    }

    /**
     * Initialise les données relatives au compte commercial connecté
     * @param event
     */

    @FXML
    void btnMonComptePressed(ActionEvent event) {

        affichagePanel(false,false,false,false,true);
        textefieldPrenomMonCompte.setPromptText(bdd.getPrenomCommercial(activeSession.getId()));
        textefieldNomMonCompte.setPromptText(bdd.getNomCommercial(activeSession.getId()));
        textefieldMailMonCompte.setPromptText(bdd.getMailCommercial(activeSession.getId()));
        textefieldVilleMonCompte.setPromptText(bdd.getVilleCommercial(activeSession.getId()));

    }


    /**
     * Vérifie chaque champs, s'il contient une valeur, alors cette valeur remplace l'ancienne valeur.
     * Par défaut, chaque champs est vide, un promptext affiche toutefois les données actuelles.
     * @param event
     */
    @FXML
    void boutonValiderMonComptePressed(ActionEvent event) {
        if(!textefieldPrenomMonCompte.getText().isEmpty()){
            bdd.setPrenomCommercial(activeSession.getId(),textefieldPrenomMonCompte.getText());
            activeSession.setPrenom(textefieldPrenomMonCompte.getText());
        }

        if(!textefieldNomMonCompte.getText().isEmpty()){
            bdd.setNomCommercial(activeSession.getId(),textefieldNomMonCompte.getText());
            activeSession.setNom(textefieldNomMonCompte.getText());
        }

        if(!textefieldMailMonCompte.getText().isEmpty()){
            bdd.setEmailCommercial(activeSession.getId(),textefieldMailMonCompte.getText());
            activeSession.setEmail(textefieldMailMonCompte.getText());
        }

        if(!textefieldVilleMonCompte.getText().isEmpty()){
            bdd.setVilleCommercial(activeSession.getId(),textefieldVilleMonCompte.getText());
            activeSession.setVille(textefieldVilleMonCompte.getText());
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

    public void handle(MouseEvent event) {
        labelInfoPrenom.setText(ListeClientsPanelClients.getSelectionModel().getSelectedItem().getPrenom());
        labelInfoNom.setText(ListeClientsPanelClients.getSelectionModel().getSelectedItem().getNom());
        labelInfoMail.setText(ListeClientsPanelClients.getSelectionModel().getSelectedItem().getMail());
        labelInfoRaisonSociale.setText(ListeClientsPanelClients.getSelectionModel().getSelectedItem().getRaisonSociale());
        labelInfoTel.setText(toString(ListeClientsPanelClients.getSelectionModel().getSelectedItem().getNumeroTel()));

    }

    private String toString(float numeroTel) {
        String s = String.valueOf(numeroTel);

        return s;
    }

    /**
     * Initialise la liste des clients
     * @param event
     * @throws SQLException
     */

    @FXML
    void btnClientsPressed(ActionEvent event) throws SQLException {

        affichagePanel(false,false,true,false,false);
        ListeClientsPanelClients.getItems().clear();
        activeSession.setListeClients(bdd.getListeClients(activeSession.getId()));


        for (modele.client client : activeSession.getListeClients()){

            ListeClientsPanelClients.getItems().add(client);

        }
        ListeClientsPanelClients.setOnMouseClicked(this::handle);


    }


    /**
     * Affiche l'onglet "ajouter client"
     * @param event
     */
    @FXML
    void btnAddClientsPressed(ActionEvent event) {
        affichagePanel(false,false,false,true,false);
        textFieldNom.clear();
        textFieldPrenom.clear();
        textFieldRaisonSociale.clear();
        textFieldMail.clear();
        textFieldNumtel.clear();
    }


    /**
     * Affiche l'onglet "planning de la semaine"
     * @param event
     */

    @FXML
    void btnPlanningSemainePressed(ActionEvent event) {
        affichagePanel(true,false,false,false,false);
        rdvSemaineInitialisation();
    }


    /**
     * Affiche l'onglet "visites recommandées"
     * @param event
     * @throws SQLException
     */

    @FXML
    void btnRdvPressed(ActionEvent event) throws SQLException {
        affichagePanel(false,true,false,false,false);
        initialiserListeClientsTries();
        initialiserTableauClients();
    }


    /**
     * Ferme algobreizh
     */
    @FXML
    private void exit(){

        Stage stage = (Stage) btn_exitMain.getScene().getWindow();
        stage.close();
    }


    /**
     * Décrémente la semaine courante de 1 sur le planning
     * @param event
     */

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


    /**
     * incrémente la semaine courante de 1 sur le planning
     * @param event
     */

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


    /**
     * Permet de choisir quel onglet s'affiche
     * @param planning onglet planning de la semaine
     * @param rdv onglet visites recommandées
     * @param clients onglet mes clients
     * @param addClient onglet ajouter client
     * @param monCompte onglet mon compte
     */

    void affichagePanel (boolean planning, boolean rdv,boolean clients, boolean addClient, boolean monCompte ){

        rdvPannel.setVisible(rdv);
        clientsPanel.setVisible(clients);
        planningPanel.setVisible(planning);
        addClientsPanel.setVisible(addClient);
        monComptePanel.setVisible(monCompte);

    }


    /**
     * Ouvre une nouvelle fenêtre pour créer un nouveau rendez-vous
     * @param event
     * @throws SQLException
     */

    @FXML
    void addRdvPressed(ActionEvent event) throws SQLException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rdv.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            myStage stage = new myStage();
            stage.setScene(new Scene(root1));
            ((rdvController)fxmlLoader.getController()).setActiveSession(this.activeSession);
            ((rdvController)fxmlLoader.getController()).addRdvInit();
            this.activeSession=stage.showAndReturn(((rdvController)fxmlLoader.getController()));
            rdvSemaineInitialisation();



        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Reinitialise toutes les cellules du planning
     */
    void clearCells(){


        for (Pane cellule : listeCellules){
            cellule.setStyle("-fx-background-color:  #D1D1D1;");
            cellule.setOnMouseClicked(null);
        }

        for (Label labelrdv : listeLabels){
            labelrdv.setText("");
        }

    }

    /**
     * Permet d'obtenir la date du jour au format LocalDate
     * @return
     */

    public LocalDate getDateActuelle(){
        return java.time.LocalDate.now();
    }

    /**
     * initialise la liste de toutes les cellules du planning
     */

    public void initCells(){
        listeCellules=Arrays.asList(cell00,cell01,cell02,cell03,cell04,cell10,cell11,cell12,cell13,cell14,cell20,cell21,cell22,cell23,cell24,cell30,cell31,cell32,cell33,cell34);
    }

    /**
     * Initialise la liste des labels associés à chaque cellule du planning
     */
    public void initLabels(){
        listeLabels=Arrays.asList(labelrdv00, labelrdv01,labelrdv02,labelrdv03,labelrdv04,labelrdv10,labelrdv11,labelrdv12,labelrdv13,labelrdv14,labelrdv20,labelrdv21,labelrdv22,labelrdv23,labelrdv24,labelrdv30,labelrdv31,labelrdv32,labelrdv33,labelrdv34);
    }

    /**
     * Retourne le numéro de semaine courant
     * @return
     */
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



    public commercial getActiveSession() {
        return activeSession;
    }



    public void setActiveSession(commercial activeSession) {
        this.activeSession = activeSession;
    }









}
