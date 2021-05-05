package sample;

import javafx.stage.Stage;

public class myStage extends Stage {

    public Commercial showAndReturn(myFXController controll) {
        super.showAndWait();
        return controll.getReturn();
    }
}



