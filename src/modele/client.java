package modele;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.sql.Date;

public class client extends RecursiveTreeObject<client> {

    private String raisonSociale;
    private String Prenom;
    private String Nom;
    private String mail;
    private Date dernierRdv;
    private String numeroTel;
    private int id;
    private int idFkCommercial;


    public Date getDernierRdv() {
        return dernierRdv;
    }

    public void setDernierRdv(Date dernierRdv) {
        this.dernierRdv = dernierRdv;
    }

    public client() {

    }

    public client(String raisonSociale, String Prenom, String Nom, String mail, String numeroTel, int id) {
        this.raisonSociale = raisonSociale;
        this.Prenom = Prenom;
        this.Nom = Nom;
        this.mail = mail;
        this.numeroTel = numeroTel;
        this.id=id;
    }

    public String getPrenom() {
        return Prenom;
    }

    public int getIdFkCommercial() {
        return idFkCommercial;
    }

    public void setIdFkCommercial(int idFkCommercial) {
        this.idFkCommercial = idFkCommercial;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    @Override
    public String toString() {
        return  Prenom+"  "+Nom;

    }
}


