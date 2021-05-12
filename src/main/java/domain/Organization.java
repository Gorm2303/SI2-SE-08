package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;

public class Organization implements Storable {
    private String name;
    private int id;
    private ArrayList<Production> productions;
    private static ArrayList<Organization> organizationsInMemory = new ArrayList<>();

    public Organization() {
        organizationsInMemory.add(this);
    }

    private Organization(int id) {
        if (id <= 0) {
            return;
        }
        IDataFacade iDataFacade = new DataFacade();
        this.name = iDataFacade.materializeOrganizationName(id);
        this.id = id;
        organizationsInMemory.add(this);
    }


    public Organization(String name, int id) {
        this.name = name;
        this.id = id;
        organizationsInMemory.add(this);
    }

    public static Organization get(int id) {
        // Maybe find a better way to shuffle through memory, maybe another collection than ArrayList.
        for (Organization org : organizationsInMemory) {
            if (org.getId() == id) {
                return org;
            }
        }
        IDataFacade iDataFacade = new DataFacade();
        if (iDataFacade.materializeOrganizationName(id) == null) {
            return null;
        }
        return new Organization(id);
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
    public int store() {
        IDataFacade iDataFacade = new DataFacade();
        this.setId(iDataFacade.storeOrganization(name));
        return this.getId();
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean equals(Organization org) {
        return (this.id == org.getId());
    }

}
