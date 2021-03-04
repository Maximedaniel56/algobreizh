package sample;

import java.sql.*;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class bdd extends login_controller {




    public static void createAccount(TextField identifiant,TextField email, PasswordField mdp){


        StringBuilder requete = new StringBuilder();
        requete.append("INSERT INTO commercial (login,email,password) VALUES ('"+ identifiant.getText()+"','"+email.getText()+"','"+mdp.getCharacters()+"')");
        execute(requete.toString());
        System.out.println("done");


    }

    public static int login(TextField ident, PasswordField mdp) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT id from commercial where login = '" + ident.getText() + "' AND password = '" + mdp.getCharacters() + "'");
            ResultSet res = execute(requete.toString());
            res.next();
            System.out.println(res.getInt("id"));
            return res.getInt("id");

        } catch(Exception e){

        }

        return 0;

    }







    public static ResultSet execute(String requete) {
        Connection connexion;
        Statement stmt = null;
        ResultSet res = null;
        try {
            connexion = DriverManager.getConnection ("jdbc:mysql://localhost:3306/algobreizh","root","");
            stmt = connexion.createStatement();
            System.out.println("connexion établie");
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



