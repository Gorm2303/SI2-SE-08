package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Credit implements Storable {
    private String role;
    private ArrayList<Contributor> contributors;
    private int id;
    private static LinkedList<Credit> creditsInMemory = new LinkedList<>();

    public Credit() {
        creditsInMemory.add(this);
    }

    public Credit(String role, int id, ArrayList<Contributor> contributors) {
        this.role = role;
        this.contributors = contributors;
        this.id = id;
        creditsInMemory.add(this);
        if (creditsInMemory.size() > 30) {
            creditsInMemory.remove(0);
        }
    }

    public Credit(String role, ArrayList<Contributor> contributors) {
        this.role = role;
        this.contributors = contributors;
        creditsInMemory.add(this);
        if (creditsInMemory.size() > 30) {
            creditsInMemory.remove(0);
        }
    }

    private Credit(int id) {
        if (id <= 0) {
            return;
        }
        IDataFacade iDataFacade = new DataFacade();
        this.role = iDataFacade.materializeCreditRole(id);
        Set<Integer> contributorIDs = iDataFacade.materializeContributorIDs(id);
        contributors = new ArrayList<>();
        for(Integer contributorID : contributorIDs) {
            contributors.add(Contributor.get(contributorID));
        }
        creditsInMemory.add(this);
        if (creditsInMemory.size() > 30) {
            creditsInMemory.remove(0);
        }
    }

    public static Credit get(int id) {
        for (Credit credit : creditsInMemory) {
            if (credit.getId() == id) {
                return credit;
            }
        }
        return new Credit(id);
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
