package sample;

import java.sql.*;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class bdd extends Controller {


    public static int getid(){
      /*  ResultSet res = null;

        StringBuilder requete = new StringBuilder();
        requete.append("SELECT id FROM commercial");
        execute(requete.toString());
        res=getResult*/
    }


    public static void createAccount(TextField identifiant,TextField email, PasswordField mdp){


        StringBuilder requete = new StringBuilder();
        requete.append("INSERT INTO commercial (login,email,password) VALUES ('"+ identifiant.getText()+"','"+email.getText()+"','"+mdp.getCharacters()+"')");
        execute(requete.toString());
        System.out.println("done");


    }








    public static ResultSet execute(String requete) {
        Connection connexion;
        Statement stmt = null;
        ResultSet res = null;
        try {
            connexion = DriverManager.getConnection ("jdbc:mysql://localhost:3306/algobreizh","root","");
            stmt = connexion.createStatement();
            if(stmt.execute(requete)) {
                System.out.println("connexion établie");
                res = stmt.getResultSet();
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return res;
    }

}



