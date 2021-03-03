package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class launch extends Application {




	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		Scene scene = new Scene(root,1000,1000);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.show();
	}





    public static void main(String[] args) {

        launch(args);



		/*Client max = new Client();
		max.setPrenom("Maxime");
		max.setNom("daniel");

		Outils.createClient(max);


        String requete = "SELECT * FROM client";

        ResultSet resultat = bdd.execute(requete);
        try {
            resultat.next();
            System.out.println(resultat.getString("prenom"));
            System.out.println(resultat.getString("nom"));



        } catch (SQLException e) {

            e.printStackTrace();
        }

    */



    }

}
