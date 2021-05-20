package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;
import java.util.LinkedList;

public class Organization implements Storable {
    private String name;
    private int id;
    private ArrayList<Production> productions;
    private static LinkedList<Organization> organizationsInMemory = new LinkedList<>();

    public Organization() {
        organizationsInMemory.add(this);
        if (organizationsInMemory.size() > 10) {
            organizationsInMemory.remove(0);
        }
    }

    public Organization(String name, int id) {
        this.name = name;
        this.id = id;
        organizationsInMemory.add(this);
        if (organizationsInMemory.size() > 10) {
            organizationsInMemory.remove(0);
        }
    }

    public Organization(String name) {
        this.name = name;
        organizationsInMemory.add(this);
        if (organizationsInMemory.size() > 10) {
            organizationsInMemory.remove(0);
        }
    }

    private Organization(int id) {
        if (id <= 0) {
            return;
        }
        IDataFacade iDataFacade = new DataFacade();
        this.name = iDataFacade.materializeOrganizationName(id);
        this.id = id;
        organizationsInMemory.add(this);
        if (organizationsInMemory.size() > 10) {
            organizationsInMemory.remove(0);
        }
    }

    public static Organization get(int id) {
        // Maybe find a better way to shuffle through memory, maybe another collection than ArrayList.
        for (Organization org : organizationsInMemory) {
            if (org.getId() == id) {
                return org;
            }
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
