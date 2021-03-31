package sample;

public class Client {

    private String raisonSociale;
    private String contactPrenom;
    private String contactNom;
    private String mail;
    private int numeroTel;


    public Client(String raisonSociale, String Prenom, String contactNom, String mail, int numeroTel) {
        this.raisonSociale = raisonSociale;
        this.contactPrenom = contactPrenom;
        this.contactNom = contactNom;
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
        return contactPrenom;
    }

    public void setContactPrenom(String contactPrenom) {
        this.contactPrenom = contactPrenom;
    }

    public String getContactNom() {
        return contactNom;
    }

    public void setContactNom(String contactNom) {
        this.contactNom = contactNom;
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
}


