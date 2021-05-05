package sample;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class bdd extends loginController {


    public static void createRendezVous(int idClient, Date date, int creneau, int idCommercial, String commentaire){

        StringBuilder requete = new StringBuilder();
        requete.append("INSERT INTO rdv (fkClient,date,creneau,fkCommercial,commentaire) VALUES ('"+idClient+"','"+date+"','"+creneau+"','"+idCommercial+"','"+commentaire+"')");
        ResultSet res =execute (requete.toString());




    }


    public static void getLastRdv(int idClient,int idCommercial){

     /*   StringBuilder requete = new StringBuilder();
        requete.append("SELECT * FROM `rdv` where fkClient = "+idClient+" and fkCommercial = "+idCommercial+" order by date desc limit 1");
        ResultSet res =execute (requete.toString());
*/
    }


    public static ArrayList<Client> getListeClients(int id) throws SQLException {

        StringBuilder requete = new StringBuilder();
        requete.append("Select * from Client Where idFkCommercial = "+id);
        ResultSet res = execute(requete.toString());
        ArrayList liste = new ArrayList();
        while (res.next()){

            Client clientTmp=new Client();
            clientTmp.setId(res.getInt("id"));
            clientTmp.setContactPrenom(res.getString("Prenom"));
            clientTmp.setContactNom(res.getString("nom"));
            clientTmp.setRaisonSociale(res.getString("raisonSociale"));
            clientTmp.setRaisonSociale(res.getString("raisonSociale"));
            clientTmp.setIdFkCommercial(res.getInt("idFkCommercial"));

            liste.add(clientTmp);
        }


        return liste;
    }


    public static ArrayList<rdv> getListeRdv(int id) throws SQLException {

        StringBuilder requete = new StringBuilder();
        requete.append("Select * from rdv Where FkCommercial = "+id);
        ResultSet res = execute(requete.toString());
        ArrayList liste = new ArrayList();
        while (res.next()){

            rdv rdv=new rdv(res.getDate("date"),res.getInt("creneau"),res.getInt("fkClient"),res.getString("commentaire"));
            liste.add(rdv);
        }


        return liste;
    }






    public static void createAccount(TextField identifiant,TextField email, PasswordField mdp){


        StringBuilder requete = new StringBuilder();
        requete.append("INSERT INTO commercial (login,email,password) VALUES ('"+ identifiant.getText()+"','"+email.getText()+"','"+mdp.getCharacters()+"')");
        ResultSet res =execute (requete.toString());
        System.out.println("done");


    }




    public static void createClient(int id,TextField Prenom,TextField Nom, TextField raisonSociale,TextField mail, TextField Num){


        StringBuilder requete = new StringBuilder();
        requete.append("INSERT INTO CLIENT (idFkCOmmercial,prenom,nom,raisonSociale, mail, numeroTel) VALUES ('"+id+"','"+Prenom.getText()+"','"+Nom.getText()+"','"+raisonSociale.getText()+"','"+mail.getText()+"','"+Num.getAnchor()+"')");
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








    public static String getPrenomCommercial(int id) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT prenom from commercial where id = '"+id+"'");
            ResultSet res = execute(requete.toString());
            res.next();
            return res.getString("prenom");


        } catch(Exception e){
            System.out.println(e);
        }
        return ("null");


    }

    public static String getPrenomClient(int id) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT prenom from client where id = '"+id+"'");
            ResultSet res = execute(requete.toString());
            res.next();
            return res.getString("prenom");


        } catch(Exception e){
            System.out.println(e);
        }
        return ("null");


    }


    public static String getCommentaireRdv(int idClient, int idCommercial, int creneau, Date date) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT * FROM `rdv` WHERE fkClient = "+idClient+" and fkCommercial = "+idCommercial+" and creneau = "+creneau+" and date = '"+date+"'");
            ResultSet res = execute(requete.toString());
            res.next();
            return res.getString("commentaire");


        } catch(Exception e){
            System.out.println(e);
        }
        return ("null");


    }

    public static String getRaisonSociale(int id) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT raisonSociale from client where id = '"+id+"'");
            ResultSet res = execute(requete.toString());
            res.next();
            return res.getString("raisonSociale");


        } catch(Exception e){
            System.out.println(e);
        }
        return ("null");


    }


    public static void setPrenomCommercial(int id, String prenom) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("UPDATE `algobreizh`.`commercial` SET `prenom` = '"+prenom+"' WHERE `commercial`.`id` = "+id);
            ResultSet res = execute(requete.toString());
            res.next();



        } catch(Exception e){

        }


    }



    public static String getNomCommercial(int id) {

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

    public static String getNomClient(int id) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT nom from client where id = '"+id+"'");
            ResultSet res = execute(requete.toString());
            res.next();


            return res.getString("nom");

        } catch(Exception e){

        }

        return "null";

    }

    public static void setNomCommercial(int id, String nom) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("UPDATE `algobreizh`.`commercial` SET `nom` = '"+nom+"' WHERE `commercial`.`id` = "+id);
            ResultSet res = execute(requete.toString());
            res.next();



        } catch(Exception e){

        }


    }

    public static String getVilleCommercial(int id) {

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

    public static void setVilleCommercial(int id, String ville) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("UPDATE `algobreizh`.`commercial` SET `ville` = '"+ville+"' WHERE `commercial`.`id` = "+id);
            ResultSet res = execute(requete.toString());
            res.next();



        } catch(Exception e){

        }


    }


    public static String getMailCommercial(int id) {

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

    public static void setEmailCommercial(int id, String mail) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("UPDATE `algobreizh`.`commercial` SET `email` = '"+mail+"' WHERE `commercial`.`id` = "+id);
            ResultSet res = execute(requete.toString());
            res.next();



        } catch(Exception e){

        }


    }

    public static int getIdLastClient() {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("SELECT id FROM client ORDER BY id DESC");
            ResultSet res = execute(requete.toString());
            res.next();


            System.out.println(res.getInt("id"));
            return res.getInt("id");

        } catch(Exception e){

        }

        return -1;

    }









    public static ResultSet execute(String requete) {
        Connection connexion;
        Statement stmt = null;
        ResultSet res = null;
        try {
            connexion = DriverManager.getConnection ("jdbc:mysql://15.237.109.152:3306/algobreizh","user","azeaze");
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
