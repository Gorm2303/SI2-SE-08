package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;
import java.util.LinkedList;

public class Organization implements Storable, Comparable<Organization> {
    private String name;
    private int id;
    private ArrayList<String> productions;
    private static LinkedList<Organization> organizationsInMemory = new LinkedList<>();

    public Organization() {
        // Memory management of Organizations
        organizationsInMemory.add(this);
        if (organizationsInMemory.size() > 10) {
            organizationsInMemory.remove(0);
        }
    }

    // For creating an Organization from an id from the database
    private Organization(int id) {
        if (id <= 0) {
            return;
        }
        IDataFacade iDataFacade = new DataFacade();
        this.name = iDataFacade.materializeOrganizationName(id);
        this.id = id;
        this.productions = new ArrayList<>(iDataFacade.materializeOrganizationIn(id));
        organizationsInMemory.add(this);
        if (organizationsInMemory.size() > 10) {
            organizationsInMemory.remove(0);
        }
    }

    // For getting an Organization in memory or if it's not there then in the database
    public static Organization get(int id) {
        for (Organization org : organizationsInMemory) {
            if (org.getId() == id) {
                return org;
            }
        }
        return new Organization(id);
    }

    public ArrayList<String> getProductions() {
        return productions;
    }

    public void setProductions(ArrayList<String> productions) {
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

    // A detailed string to present information about the Organization
    @Override
    public String detailedString() {
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder(name + "\n" + "Medvirkede i produktionen af: \n");
        for (String s : productions) {
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean equals(Organization org) {
        return (this.id == org.getId());
    }

    @Override
    public int compareTo(Organization o) {
        return this.name.compareTo(o.getName());
    }
}
