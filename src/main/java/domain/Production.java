package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class Production implements Storable, Comparable<Production> {
    private String name;
    private int id;
    private Organization producer;
    private String releaseDate;
    private int length;
    private ArrayList<Organization> orgContributors;
    private ArrayList<Credit> credits;
    private static LinkedList<Production> productionsInMemory = new LinkedList<>();

    public Production() {
        // Memory management of Productions
        productionsInMemory.add(this);
        if (productionsInMemory.size() > 5) {
            productionsInMemory.remove(0);
        }
    }

    // For creating a Production from an id from the database
    private Production(int id) {
        if (id <= 0) {
            return;
        }
        IDataFacade iDataFacade = new DataFacade();
        this.id = id;
        this.name = iDataFacade.materializeProductionName(id);
        this.producer = Organization.get(iDataFacade.materializeProductionProducerID(id));
        this.releaseDate = iDataFacade.materializeProductionReleaseDate(id);
        this.length = iDataFacade.materializeProductionLength(id);

        ArrayList<Integer> contributingOrgIDs = new ArrayList<>(iDataFacade.materializeProductionOrganizationIDs(id));
        this.orgContributors = new ArrayList<>();
        for (Integer orgID : contributingOrgIDs) {
            this.orgContributors.add(Organization.get(orgID));
        }
        Set<Integer> creditIDs = iDataFacade.materializeProductionCreditIDs(id);
        this.credits = new ArrayList<>();
        for(Integer creditID : creditIDs) {
            this.credits.add(Credit.get(creditID));
        }
        productionsInMemory.add(this);
        if (productionsInMemory.size() > 5) {
            productionsInMemory.remove(0);
        }
    }

    // For getting a Production in memory or if it's not there then in the database
    public static Production get(int id) {
        for (Production production : productionsInMemory) {
            if (production.getId() == id) {
                return production;
            }
        }
        return new Production(id);
    }

    public ArrayList<Organization> getOrgContributors() {
        return orgContributors;
    }

    public void setOrgContributors(ArrayList<Organization> orgContributors) {
        this.orgContributors = orgContributors;
    }

    public ArrayList<Credit> getCredits() {
        return credits;
    }

    public void setCredits(ArrayList<Credit> credits) {
        this.credits = credits;
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

    public Organization getProducer() {
        return producer;
    }

    public void setProducer(Organization producer) {
        this.producer = producer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return name;
    }

    // A detailed string to present information about the Production
    @Override
    public String detailedString() {
        StringBuilder stringBuilder = new StringBuilder(name + "\n" +
                length + " minutters spilletid" + "\n\n" +
                "Udgivet: " + releaseDate + "\n\n" +
                "Produceret af: " + producer.getName() + "\n\n" +
                "I samarbejde med:\n");
        for (Organization organization : orgContributors) {
            stringBuilder.append("\t\t").append(organization.getName()).append("\n");
        }
        stringBuilder.append("\n\n");
        for (Credit credit : credits) {
            StringBuilder playedBy = new StringBuilder();
            for (int i = 0; i < credit.getContributors().size(); i++) {
                playedBy.append("\n\t\t").append(credit.getContributors().get(i).getName());
            }
            stringBuilder.append(credit.getRole()).append(":").append(playedBy).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public int store() {
        IDataFacade iDataFacade = new DataFacade();
        // If the Production is new and therefore not in the database, store it
        if (this.id == 0) {
            this.setId(iDataFacade.storeProductionData(this.name, this.releaseDate, this.length, this.producer.getId()));

            ArrayList<Integer> organizationIDs = new ArrayList<>();
            for(Organization organization : orgContributors) {
                organizationIDs.add(organization.getId());
            }

            iDataFacade.storeProductionOrganizations(organizationIDs, this.id);

            for (Credit credit : credits) {
                credit.store(this.id);
            }

        } // Else update existing Production in the database
        else {
            //update production simple data
            iDataFacade.updateProductionSimpleData(
                    this.id, this.name, this.releaseDate, this.length, this.producer.getId());

            //delete all old orgs
            iDataFacade.deleteOrganizationInProduction(this.id);

            //insert all new orgs
            ArrayList<Integer> organizationIDs = new ArrayList<>();
            for(Organization organization : orgContributors) {
                organizationIDs.add(organization.getId());
            }
            iDataFacade.storeProductionOrganizations(organizationIDs, this.id);

            //delete all old creds
            iDataFacade.deleteCreditsInProduction(this.id);

            //insert all new creds
            for (Credit credit : credits) {
                credit.store(this.id);
            }
        }
        return this.id;
    }

    @Override
    public int compareTo(Production o) {
        return this.name.compareTo(o.getName());
    }
}
