package sample;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class bdd extends loginController {


    public static void createRendezVous(Date date, Client client, int creneau){


    }


    public static ArrayList<Client> getListeClients() throws SQLException {

        StringBuilder requete = new StringBuilder();
        requete.append("Select * from Client");
        ResultSet res = execute(requete.toString());
        ArrayList liste = new ArrayList();
        while (res.next()){

            Client clientTmp=new Client();
            clientTmp.setContactPrenom(res.getString("Prenom"));
            clientTmp.setContactNom(res.getString("nom"));
            clientTmp.setRaisonSociale(res.getString("nom"));
            liste.add(clientTmp);
            }


        return liste;
    }

    public static void createAccount(TextField identifiant,TextField email, PasswordField mdp){


        StringBuilder requete = new StringBuilder();
        requete.append("INSERT INTO commercial (login,email,password) VALUES ('"+ identifiant.getText()+"','"+email.getText()+"','"+mdp.getCharacters()+"')");
        ResultSet res =execute (requete.toString());
        System.out.println("done");


    }

    public static void createClient(TextField Prenom,TextField Nom, TextField raisonSociale,TextField mail, TextField Num){


        StringBuilder requete = new StringBuilder();
        requete.append("INSERT INTO CLIENT (prenom,nom,raisonSociale, mail, numeroTel) VALUES ('"+ Prenom.getText()+"','"+Nom.getText()+"','"+raisonSociale.getText()+"','"+mail.getText()+"','"+Num.getAnchor()+"')");
        execute(requete.toString());
        System.out.println("done");


    }




    public static int checkLoginExist(String identifiant) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT id FROM commercial where login = '"+ identifiant+"'");
            ResultSet res = execute(requete.toString());
            res.next();
            int tmpID = (res.getInt("id"));
            return res.getInt("id");
        }


          catch(Exception e){

        }

        return 0;

    }



    public static int checkMailExist(String mail){
        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT id FROM commercial where email = '"+mail+"'");
            ResultSet res = execute(requete.toString());
            res.next();
            int tmpID = (res.getInt("id"));
            return res.getInt("id");
        }


        catch(Exception e){

        }

        return 0;

    }





    public static int login(TextField ident, PasswordField mdp) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT id from commercial where login = '" + ident.getText() + "' AND password = '" + mdp.getCharacters() + "'");
            ResultSet res = execute(requete.toString());
            res.next();
            int tmpID = (res.getInt("id"));


            return res.getInt("id");

        } catch(Exception e){

        }

        return 0;

    }

    public static String getPrenom(int id) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT prenom from commercial where id = '"+id+"'");
            ResultSet res = execute(requete.toString());
            res.next();

            return res.getString("prenom");

        } catch(Exception e){

        }

        return "null";

    }

    public static String getNom(int id) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT nom from commercial where id = '"+id+"'");
            ResultSet res = execute(requete.toString());
            res.next();


            return res.getString("nom");

        } catch(Exception e){

        }

        return "null";

    }

    public static String getVille(int id) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT ville from commercial where id = '"+id+"'");
            ResultSet res = execute(requete.toString());
            res.next();


            return res.getString("ville");

        } catch(Exception e){

        }

        return "null";

    }


    public static String getMail(int id) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT email from commercial where id = '"+id+"'");
            ResultSet res = execute(requete.toString());
            res.next();



            return res.getString("email");

        } catch(Exception e){

        }

        return "null";

    }










    public static ResultSet execute(String requete) {
        Connection connexion;
        Statement stmt = null;
        ResultSet res = null;
        try {
            connexion = DriverManager.getConnection ("jdbc:mysql://localhost:3306/algobreizh","root","");
            stmt = connexion.createStatement();
            if(stmt.execute(requete)) {
                res = stmt.getResultSet();
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return res;
    }

}



