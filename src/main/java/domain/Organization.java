package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;

public class Organization implements Savable {
    private String name;
    private int id;
    private ArrayList<Production> productions;

    public Organization() {}

    public Organization(String name, int id) {
        this.name = name;
        this.id = id;
    }


    public ArrayList<Production> getProductions() {
        return productions;
    }

    public void setProductions(ArrayList<Production> productions) {
        this.productions = productions;
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

    @Override
    public boolean save() {
        IDataFacade iDataFacade = new DataFacade();
        return iDataFacade.storeOrganization(name);
    }

    @Override
    public Object load() {
        return null;
    }
}
