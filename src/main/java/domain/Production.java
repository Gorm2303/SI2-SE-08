package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.*;

public class Production implements Storable, Comparable<Production> {
    private String name;
    private int id;
    private Organization producer;
    private String releaseDate;
    private int length;
    private ArrayList<Organization> orgContributors;
    private ArrayList<Credit> credits;
    private static ArrayList<Production> productionsInMemory = new ArrayList<>();

    public Production() {
        productionsInMemory.add(this);
        if (productionsInMemory.size() > 5) {
            productionsInMemory.remove(0);
        }
    }

    public Production(String name, int id, Organization producer, String releaseDate, int length,
                      ArrayList<Organization> orgContributors, ArrayList<Credit> credits) {
        this.name = name;
        this.id = id;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.length = length;
        this.orgContributors = orgContributors;
        this.credits = credits;
        productionsInMemory.add(this);
        if (productionsInMemory.size() > 5) {
            productionsInMemory.remove(0);
        }
    }

    public Production(String name, Organization producer, String releaseDate, int length,
                      ArrayList<Organization> orgContributors, ArrayList<Credit> credits) {
        this.name = name;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.length = length;
        this.orgContributors = orgContributors;
        this.credits = credits;
        productionsInMemory.add(this);
        if (productionsInMemory.size() > 5) {
            productionsInMemory.remove(0);
        }
    }

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

        ArrayList<Integer> contributingOrgIDs = iDataFacade.materializeProductionOrganizationIDs(id);
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

    public String detailedString() {
        StringBuilder returnString = new StringBuilder(name + "\n\n" +
                "Udgivet: " + releaseDate + "\n\n" +
                "Produceret af: " + producer.getName() + "\n\n" +
                "I samarbejde med:\n");
        for (Organization organization : orgContributors) {
            returnString.append("\t\t").append(organization.getName()).append("\n");
        }
        returnString.append("\n\n");
        for (Credit credit : credits) {
            StringBuilder playedBy = new StringBuilder();
            for (int i = 0; i < credit.getContributors().size(); i++) {
                playedBy.append("\n\t\t").append(credit.getContributors().get(i).getName());
            }
            returnString.append(credit.getRole()).append(":").append(playedBy).append("\n");
        }
        return returnString.toString();
    }

    @Override
    public int store() {
        IDataFacade iDataFacade = new DataFacade();
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

        }
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
