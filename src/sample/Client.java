package sample;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private String raisonSociale;
    private String Prenom;
    private String Nom;
    private String mail;
    private int numeroTel;


    public Client() {

    }

    public Client(String raisonSociale, String Prenom, String Nom, String mail, int numeroTel) {
        this.raisonSociale = raisonSociale;
        this.Prenom = Prenom;
        this.Nom = Nom;
        this.mail = mail;
        this.numeroTel = numeroTel;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getContactPrenom() {
        return Nom;
    }

    public void setContactPrenom(String contactPrenom) {
        this.Prenom = contactPrenom;
    }

    public String getContactNom() {
        return Nom;
    }

    public void setContactNom(String contactNom) {
        this.Nom = contactNom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(int numeroTel) {
        this.numeroTel = numeroTel;
    }

    @Override
    public String toString() {
        return "Client{" +
                "raisonSociale='" + raisonSociale + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Nom='" + Nom + '\'' +
                ", mail='" + mail + '\'' +
                ", numeroTel=" + numeroTel +
                '}';
    }
}


