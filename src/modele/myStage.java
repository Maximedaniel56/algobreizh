package modele;

import controllers.myFXController;
import javafx.stage.Stage;

public class myStage extends Stage {

    public commercial showAndReturn(myFXController controll) {
        super.showAndWait();
        return controll.getReturn();
    }
}



