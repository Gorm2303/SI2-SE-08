package domain;

import Acquaintance.IOrganization;
import Acquaintance.IProduction;

import java.util.ArrayList;

public class Organization implements IOrganization {
    private String name;
    private int id;
    private ArrayList<Production> productions;

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
    public ArrayList<IProduction> getIProductions() {
        return new ArrayList<>(productions);
    }
}
