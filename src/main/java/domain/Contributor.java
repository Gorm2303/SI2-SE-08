package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;
import java.util.Date;

public class Contributor implements Storable {
    private String name;
    private int id;
    private String birthDate;
    private ArrayList<Production> productionsIsIn;

    public Contributor() {}

    public Contributor(String name, int id, String birthDate) {
        this.name = name;
        this.id = id;
        this.birthDate = birthDate;
    }

    public Contributor(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public ArrayList<Production> getIsIn(){
        return productionsIsIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public int store() {
        IDataFacade iDataFacade = new DataFacade();
        this.setId(iDataFacade.storeContributor(name, birthDate));
        return this.getId();
    }

}
