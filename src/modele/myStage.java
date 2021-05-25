package modele;

import controllers.myFXController;
import controllers.myFXController2;
import javafx.stage.Stage;

public class myStage extends Stage {

    /**
     * classe et méthodes permettant le retour automatique d'une variable à la sortie d'une fenêtre
     * @param controll
     * @return
     */
    public commercial showAndReturn(myFXController controll) {
        super.showAndWait();
        return controll.getReturn();
    }

    public boolean showAndReturnBoolean(myFXController2 controll) {
        super.showAndWait();
        return controll.getReturn();
    }

}



