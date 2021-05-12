package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Credit implements Storable {
    private String role;
    private ArrayList<Contributor> contributors;
    private int id;

    public Credit() {}

    public Credit(String role, ArrayList<Contributor> contributors) {
        this.role = role;
        this.contributors = contributors;
    }

    public Credit(int id) {
        IDataFacade iDataFacade = new DataFacade();
        this.role = iDataFacade.materializeCreditRole(id);
        Set<Integer> contributorIDs = iDataFacade.materializeContributorIDs(id);
        contributors = new ArrayList<>();
        for(Integer contributorID : contributorIDs) {
            contributors.add(new Contributor(contributorID));
        }
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
        Set<Integer> contributorIDs = new HashSet<>();
        for (Contributor contributor : contributors) {
            contributorIDs.add(contributor.getId());
        }
        IDataFacade iDataFacade = new DataFacade();
        this.setId(iDataFacade.storeCredit(this.role, productionID, contributorIDs));
        return this.getId();
    }

}
