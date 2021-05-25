package modele;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import controllers.loginController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class bdd extends loginController {

    /**
     * Crée un rdv en bdd
     * @param idClient
     * @param date date
     * @param creneau demi-demi journée (8/10h - 10/12h - 13/15h - 15/17h)
     * @param idCommercial
     * @param commentaire commentaire associé au rendez-vous
     */
    public static void createRendezVous(int idClient, Date date, int creneau, int idCommercial, String commentaire){

        StringBuilder requete = new StringBuilder();
        requete.append("INSERT INTO rdv (fkClient,date,creneau,fkCommercial,commentaire) VALUES ('"+idClient+"','"+date+"','"+creneau+"','"+idCommercial+"','"+commentaire+"')");
        ResultSet res =execute (requete.toString());




    }

    /**
     * Vérifie si un créneau est disponible pour un commercial. Retourne vrai si le créneau est libre, faux le cas contraire
     * @param date date
     * @param creneau demi-demi journée (8/10h - 10/12h - 13/15h - 15/17h)
     * @param idCommercial
     * @return
     * @throws SQLException
     */
    public static boolean checkDateDispo(Date date, int creneau, int idCommercial) throws SQLException {

        StringBuilder requete = new StringBuilder();
        requete.append("select * from rdv where fkcommercial = "+idCommercial+" and date ='"+date+"' and creneau ="+creneau+"");
        ResultSet res =execute (requete.toString());
        if (res.next()==false){
                return true;
        }

        else {
            return false;
        }
    }


    /*public static void getLastRdv(int idCommercial){

       StringBuilder requete = new StringBuilder();
        requete.append("select client.id, MAX(rdv.date) as datemax from client left join rdv on fkclient = client.id where fkcommercial="+idCommercial+" or idFkCommercial ="+idCommercial+" group by client.id ORDER BY datemax ASC");
        ResultSet res =execute (requete.toString());

    */

    /**
     * Retourne une arraylist d'id des clients avec qui l'id commercial passé en paramètre a rendez-vous, liste triée par date de rdv de manière croissante.
     * @param id
     * @return
     * @throws SQLException
     */
    public static ArrayList<Integer> getListeIdClientsTries(int id) throws SQLException {

        StringBuilder requete = new StringBuilder();
        requete.append("select client.id, MAX(rdv.date) from client left join rdv on fkclient = client.id where fkcommercial="+id+" group by client.id ORDER BY rdv.date ASC");
        ResultSet res = execute(requete.toString());
        ArrayList liste = new ArrayList();
        while (res.next()){

            liste.add(res.getInt("id"));
        }


        return liste;
    }

    /**
     * Retourne une arraylist de tous les clients associés à un id commercial
     * @param id
     * @return
     * @throws SQLException
     */
    public static ArrayList<client> getListeClients(int id) throws SQLException {

        StringBuilder requete = new StringBuilder();
        requete.append("select *, MAX(rdv.date) as datemax from client left join rdv on fkclient = client.id where fkcommercial="+id+" or idFkCommercial ="+id+" group by client.id");
        ResultSet res = execute(requete.toString());
        ArrayList liste = new ArrayList();
        while (res.next()){

            client clientTmp=new client();
            clientTmp.setId(res.getInt("id"));
            clientTmp.setIdFkCommercial(id);
            clientTmp.setContactPrenom(res.getString("prenom"));
            clientTmp.setContactNom(res.getString("nom"));
            clientTmp.setRaisonSociale(res.getString("raisonSociale"));
            clientTmp.setMail(res.getString("mail"));
            clientTmp.setNumeroTel(res.getString("numeroTel"));
            if (res.getDate("datemax") != null){
                clientTmp.setDernierRdv(res.getDate("datemax"));
            }
            else{
                clientTmp.setDernierRdv(new Date(000));
            }

            liste.add(clientTmp);
        }


        return liste;
    }


    /**
     * retourne une arraylist de rdv de tous les rendezèvous associés à un id commercial
     * @param id
     * @return
     * @throws SQLException
     */

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


    /**
     * creer un compte commercial en bdd
     * @param identifiant
     * @param email
     * @param mdp
     */

    public static void createAccount(TextField identifiant,TextField email, PasswordField mdp){


        StringBuilder requete = new StringBuilder();
        requete.append("INSERT INTO commercial (login,email,password) VALUES ('"+ identifiant.getText()+"','"+email.getText()+"','"+mdp.getCharacters()+"')");
        ResultSet res =execute (requete.toString());
        System.out.println("done");


    }


    /**
     * Creer un client en bdd
     * @param id
     * @param Prenom
     * @param Nom
     * @param raisonSociale
     * @param mail
     * @param Num
     */

    public static void createClient(int id,TextField Prenom,TextField Nom, TextField raisonSociale,TextField mail, TextField Num){


        StringBuilder requete = new StringBuilder();
        requete.append("INSERT INTO CLIENT (idFkCOmmercial,prenom,nom,raisonSociale, mail, numeroTel) VALUES ('"+id+"','"+Prenom.getText()+"','"+Nom.getText()+"','"+raisonSociale.getText()+"','"+mail.getText()+"','"+Num.getText()+"')");
        execute(requete.toString());
        System.out.println("done");


    }


    /**
     * Retourne l'id d'un identifiant si il existe, retourne 0 sinon
     * @param identifiant
     * @return
     */

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


    /**
     * Retourne l'id d'une adresse e-mail si elle existe, retourne 0 sinon
     * @param mail
     * @return
     */
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


    /**
     * Permet de se connecter à algobreizh, Si le couple identifiant/mot de passe existe, retourne l'id obtenu et connecte le compte associé à l'id en question
     * Si le couple n'existe pas, retourne 0
     * @param ident
     * @param mdp
     *
     * @return
     */

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


    /**
     * Retourne le nom associé à un id commercial
     * @param id
     * @return
     */

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

    /**
     * Retourne le prénom associé à un id client
     * @param id
     * @return
     */
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



    public static void supprimerRdv(int id, LocalDate date, int creneau){

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("DELETE FROM `rdv` WHERE fkCommercial = "+id+" and date = '"+date+"' and creneau = "+creneau);
            ResultSet res = execute(requete.toString());



        } catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Retourne la raison sociale associée à un id client
     * @param id
     * @return
     */
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

    /**
     * Applique à un compte commercial le prenom entré en paramètres
     * @param id
     * @param prenom
     */
    public static void setPrenomCommercial(int id, String prenom) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("UPDATE `algobreizh`.`commercial` SET `prenom` = '"+prenom+"' WHERE `commercial`.`id` = "+id);
            ResultSet res = execute(requete.toString());
            res.next();



        } catch(Exception e){

        }


    }

    /**
     * Retourne le nom associé à un id commercial
     * @param id
     * @return
     */

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

    /**
     * Retourne le nom associé à un id client
     * @param id
     * @return
     */

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

    /**
     * Applique à un compte commercial le nom entré en paramètres
     * @param id
     * @param nom
     */
    public static void setNomCommercial(int id, String nom) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("UPDATE `algobreizh`.`commercial` SET `nom` = '"+nom+"' WHERE `commercial`.`id` = "+id);
            ResultSet res = execute(requete.toString());
            res.next();



        } catch(Exception e){

        }


    }

    /**
     * Retourne la ville associée à un id commercial
     * @param id
     * @return
     */
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

    /**
     * Applique à un compte commercial une ville entrée en paramètres
     * @param id
     * @param ville
     */
    public static void setVilleCommercial(int id, String ville) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("UPDATE `algobreizh`.`commercial` SET `ville` = '"+ville+"' WHERE `commercial`.`id` = "+id);
            ResultSet res = execute(requete.toString());
            res.next();



        } catch(Exception e){

        }


    }

    /**
     * Retourne le mail associé à un id commercial
     * @param id
     * @return
     */
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

    /**
     * Applique à un compte commercial l'adresse email entree en paramètres
     * @param id
     * @param mail
     */

    public static void setEmailCommercial(int id, String mail) {

        try {

            StringBuilder requete = new StringBuilder();
            requete.append("UPDATE `algobreizh`.`commercial` SET `email` = '"+mail+"' WHERE `commercial`.`id` = "+id);
            ResultSet res = execute(requete.toString());
            res.next();



        } catch(Exception e){

        }


    }

    /**
     * Retourne l'id du dernier client créé
     * @return retourne un id ou -1 si aucun résultat
     */

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


    /**
     * Méthode principale permettant la connexion et l'envoi de requêtes à la bdd
     * @param requete Requète envoyée et executée
     * @return retourne un objet ResultSet contenant le résultat de la requète SQL envoyée
     */

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
