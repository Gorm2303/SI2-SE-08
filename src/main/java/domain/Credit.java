package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;

public class Credit implements Storable {
    private String role;
    private ArrayList<Contributor> contributors;
    private int id;

    public Credit() {}

    public Credit(String role, ArrayList<Contributor> contributors) {
        this.role = role;
        this.contributors = contributors;
    }

    public ArrayList<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(ArrayList<Contributor> contributors) {
        this.contributors = contributors;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int store() {
        return 0;
    }

    public int store(int productionID) {
        IDataFacade iDataFacade = new DataFacade();
        this.setId(iDataFacade.storeCredit(this.role, productionID));
        return this.getId();
    }

}
