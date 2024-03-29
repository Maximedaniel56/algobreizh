package modele;

import java.util.ArrayList;

public class commercial {

    private int id;
    private String prenom;
    private String nom;
    private String ville;
    private String email;
    private String motdepasse;
    private String identifiant;
    private ArrayList<client> listeClients;
    private ArrayList<client> clientsPrioritaires;
    private ArrayList<rdv> listeRdv;


    public commercial(String prenom, String nom, String ville, String mail, int id) {
        this.email = mail;
        this.prenom = prenom;
        this.nom = nom;
        this.ville = ville;
        this.id=id;

        listeRdv = new ArrayList();
        listeClients = new ArrayList();



    }

    public ArrayList<client> getClientsPrioritaires() {
        return clientsPrioritaires;
    }

    public void setClientsPrioritaires(ArrayList<client> clientsPrioritaires) {
        this.clientsPrioritaires = clientsPrioritaires;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<client> getListeClients() {
        return listeClients;
    }

    public void setListeClients(ArrayList<client> listeClients) {
        this.listeClients = listeClients;
    }

    public ArrayList<rdv> getListeRdv() {
        return listeRdv;
    }

    public void setListeRdv(ArrayList<rdv> listeRdv) {
        this.listeRdv = listeRdv;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
