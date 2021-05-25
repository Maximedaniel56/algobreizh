package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modele.bdd;
import modele.commercial;
import modele.myStage;
import modele.rdv;

import java.sql.SQLException;

public class rdvResumeController implements myFXController{



    @FXML
    private JFXButton boutonSupprimerRdv;
    private commercial activeSession;
    private rdv rdvCourant;

    @FXML
    private Label rdvResumePrenom;

    @FXML
    private Label rdvResumeSociete;

    @FXML
    private Label rdvResumeDate;
    @FXML
    private Label rdvResumeCreneau;
    @FXML
    private Label textAreaCommentaire;
    private boolean confirmationSuppression =false;

    public rdv getRdvCourant() {
        return rdvCourant;
    }

    public void setRdvCourant(rdv rdvCourant) {
        this.rdvCourant = rdvCourant;
    }

    @FXML
    public Label getRdvResumePrenom() {
        return rdvResumePrenom;
    }
    @FXML
    public void setRdvResumePrenom(Label rdvResumePrenom) {
        this.rdvResumePrenom = rdvResumePrenom;
    }
    @FXML
    public Label getRdvResumeSociete() {
        return rdvResumeSociete;
    }
    @FXML
    public void setRdvResumeSociete(Label rdvResumeSociete) {
        this.rdvResumeSociete = rdvResumeSociete;
    }
    @FXML
    public Label getRdvResumeDate() {
        return rdvResumeDate;
    }
    @FXML
    public void setRdvResumeDate(Label rdvResumeDate) {
        this.rdvResumeDate = rdvResumeDate;
    }

    public commercial getActiveSession() {
        return activeSession;
    }

    public void setActiveSession(commercial activeSession) {
        this.activeSession = activeSession;
    }

    public JFXButton getBoutonSupprimerRdv() {
        return boutonSupprimerRdv;
    }

    public void setBoutonSupprimerRdv(JFXButton boutonSupprimerRdv) {
        this.boutonSupprimerRdv = boutonSupprimerRdv;
    }

    public Label getRdvResumeCreneau() {
        return rdvResumeCreneau;
    }

    public void setRdvResumeCreneau(Label rdvResumeCreneau) {
        this.rdvResumeCreneau = rdvResumeCreneau;
    }

    public Label getTextAreaCommentaire() {
        return textAreaCommentaire;
    }

    public void setTextAreaCommentaire(Label textAreaCommentaire) {
        this.textAreaCommentaire = textAreaCommentaire;
    }

    /**
     * Ouvre une nouvelle fenêtre demander de confirmer la supression d'un rendez-vous
     * Supprime en bdd et en local si oui.
     * @param event
     * @throws SQLException
     */


    @FXML
    void boutonSupprimerRdvPressed(ActionEvent event) throws SQLException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/confirmerSuppression.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            myStage stage = new myStage();
            stage.setScene(new Scene(root1));

            this.confirmationSuppression =stage.showAndReturnBoolean(((suppressionRdvController)fxmlLoader.getController()));




        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(confirmationSuppression){
            for (rdv rdv : activeSession.getListeRdv()){
                if (rdv == rdvCourant){
                    activeSession.getListeRdv().remove(rdv);
                    bdd.supprimerRdv(activeSession.getId(),rdv.getDate().toLocalDate(),rdv.getCreneau());
                    break;
                }
            }
            exit();
        }
    }

    public boolean isConfirmationSuppression() {
        return confirmationSuppression;
    }

    public void setConfirmationSuppression(boolean confirmationSuppression) {
        this.confirmationSuppression = confirmationSuppression;
    }
    /**
     * ferme la fenêtre
     */
    @FXML
    private void exit(){

        Stage stage = (Stage) boutonSupprimerRdv.getScene().getWindow();
        stage.close();
    }

    @Override
    public commercial getReturn() {
        return activeSession;
    }

}
