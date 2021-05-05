package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class rdvResumeController {



    @FXML
    private JFXButton boutonSupprimerRdv;



    @FXML
    private Label rdvResumePrenom;

    @FXML
    private Label rdvResumeSociete;

    @FXML
    private Label rdvResumeDate;
    @FXML
    private JFXTextArea textAreaCommentaire;

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
    @FXML
    public JFXTextArea getTextAreaCommentaire() {
        return textAreaCommentaire;
    }
    @FXML
    public void setTextAreaCommentaire(JFXTextArea textAreaCommentaire) {
        this.textAreaCommentaire = textAreaCommentaire;
    }

    @FXML
    void boutonValiderAjoutRdvPressed(ActionEvent event) {
    }


}
