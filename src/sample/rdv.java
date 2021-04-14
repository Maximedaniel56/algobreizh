package sample;

import java.sql.Date;

public class rdv {

    private Date date;
    private int creneau;
    private int idClient;

    public rdv(Date date, int creneau, int id) {
        this.date = date;
        this.creneau = creneau;
        this.idClient=id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCreneau() {
        return creneau;
    }

    public void setCreneau(int creneau) {
        this.creneau = creneau;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "rdv{" +
                "date=" + date +
                ", creneau=" + creneau +
                ", idClient=" + idClient +
                '}';
    }
}
