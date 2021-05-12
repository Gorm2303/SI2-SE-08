package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;

public class Contributor implements Storable {
    private String name;
    private int id;
    private String birthDate;
    private ArrayList<Production> productionsIsIn;
    private static ArrayList<Contributor> contributorsInMemory = new ArrayList<>();

    public Contributor() {
        contributorsInMemory.add(this);
    }

    private Contributor(int id) {
        if (id <= 0) {
            return;
        }
        IDataFacade iDataFacade = new DataFacade();
        this.name = iDataFacade.materializeContributorName(id);
        this.birthDate = iDataFacade.materialiseContributorBirthDate(id);
        this.id = id;
        contributorsInMemory.add(this);
    }

    public Contributor(String name, int id, String birthDate) {
        this.name = name;
        this.id = id;
        this.birthDate = birthDate;
        contributorsInMemory.add(this);
    }

    public Contributor(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        contributorsInMemory.add(this);
    }

    public static Contributor get(int id) {
        // Maybe find a better way to shuffle through memory, maybe another collection than ArrayList.
        for (Contributor contributor : contributorsInMemory) {
            if (contributor.getId() == id) {
                return contributor;
            }
        }
        IDataFacade iDataFacade = new DataFacade();
        if (iDataFacade.materializeOrganizationName(id) == null) {
            return null;
        }
        return new Contributor(id);
    }

    public Contributor(int id) {
        IDataFacade iDataFacade = new DataFacade();
        this.name = iDataFacade.materializeContributorName(id);
        this.birthDate = iDataFacade.materializeContributorBirthDate(id);
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

    @Override
    public String toString() {
        return name;
    }
}
