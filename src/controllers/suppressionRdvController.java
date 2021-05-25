package controllers;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class suppressionRdvController implements myFXController2{



    @FXML
    private Label labelConfirmationSuppression;
    @FXML
    private JFXButton boutonConfirmerSupression;
    @FXML
    private JFXButton boutonAnnulerSupression;
    private boolean confirmationSupression=false;




    @FXML
    void boutonAnnulerSupressionPressed(ActionEvent event) {
        exit();
    }

    @FXML
    void boutonConfirmerSupressionPressed(ActionEvent event) {
        confirmationSupression=true;
        exit();
    }

    public boolean isConfirmationSupression() {
        return confirmationSupression;
    }

    public void setConfirmationSupression(boolean confirmationSupression) {
        this.confirmationSupression = confirmationSupression;
    }

    @FXML
    private void exit(){

        Stage stage = (Stage) boutonAnnulerSupression.getScene().getWindow();
        stage.close();
    }

    @Override
    public boolean getReturn() {
        return confirmationSupression;
    }

}






