package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.LinkedList;

public class Contributor implements Storable {
    private String name;
    private int id;
    private String birthDate;
    private static LinkedList<Contributor> contributorsInMemory = new LinkedList<>();

    public Contributor() {
        contributorsInMemory.add(this);
        if (contributorsInMemory.size() > 10) {
            contributorsInMemory.remove(0);
        }
    }

    public Contributor(String name, int id, String birthDate) {
        this.name = name;
        this.id = id;
        this.birthDate = birthDate;
        contributorsInMemory.add(this);
        if (contributorsInMemory.size() > 10) {
            contributorsInMemory.remove(0);
        }
    }

    public Contributor(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        contributorsInMemory.add(this);
        if (contributorsInMemory.size() > 10) {
            contributorsInMemory.remove(0);
        }
    }
    private Contributor(int id) {
        if (id <= 0) {
            return;
        }
        IDataFacade iDataFacade = new DataFacade();
        this.name = iDataFacade.materializeContributorName(id);
        this.birthDate = iDataFacade.materializeContributorBirthDate(id);
        this.id = id;
        contributorsInMemory.add(this);
        if (contributorsInMemory.size() > 10) {
            contributorsInMemory.remove(0);
        }
    }

    public static Contributor get(int id) {
        // Maybe find a better way to shuffle through memory, maybe another collection than ArrayList.
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
}
