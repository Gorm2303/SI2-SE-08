package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;
import java.util.LinkedList;

public class Contributor implements Storable, Comparable<Contributor>  {
    private String name;
    private int id;
    private String birthDate;
    private ArrayList<String> productionNames;
    private static LinkedList<Contributor> contributorsInMemory = new LinkedList<>();

    public Contributor() {
        // Memory management of Contributors
        contributorsInMemory.add(this);
        if (contributorsInMemory.size() > 10) {
            contributorsInMemory.remove(0);
        }
    }

    // For creating a Contributor from an id from the database
    private Contributor(int id) {
        if (id <= 0) {
            return;
        }
        IDataFacade iDataFacade = new DataFacade();
        this.name = iDataFacade.materializeContributorName(id);
        this.birthDate = iDataFacade.materializeContributorBirthDate(id);
        this.id = id;
        this.productionNames = new ArrayList<>(iDataFacade.materializeContributorIn(id));
        contributorsInMemory.add(this);
        if (contributorsInMemory.size() > 10) {
            contributorsInMemory.remove(0);
        }
    }

    // For getting a Contributor in memory or if it's not there then in the database
    public static Contributor get(int id) {
        for (Contributor contributor : contributorsInMemory) {
            if (contributor.getId() == id) {
                return contributor;
            }
        }
        return new Contributor(id);
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

    @Override
    public String toString() {
        return name;
    }

    // A detailed string to present information about the organization
    @Override
    public String detailedString() {
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder(name + " " + birthDate + "\n" + "Produktioner personen er medvirkende i: \n");
        for (String s : productionNames) {
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Contributor o) {
        return this.name.compareTo(o.getName());
    }
}
