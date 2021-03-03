package sample;

public class Commercial {

    private double id;
    private String prenom;
    private String nom;
    private String ville;
    private String email;
    private String motdepasse;
    private String identifiant;

    public Commercial(String email, String motdepasse, String identifiant, int id) {
        this.email = email;
        this.motdepasse = motdepasse;
        this.identifiant = identifiant;
        this.id=id;
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
