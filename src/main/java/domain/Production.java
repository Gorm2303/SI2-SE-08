package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.*;

public class Production implements Storable {
    private String name;
    private int id;
    private Organization producer;
    private String releaseDate;
    private String programCategory;
    private int length;
    private ArrayList<Organization> orgContributors;
    private ArrayList<Credit> credits;

    public Production() {
    }

    public Production(String name, int id, Organization producer, String releaseDate, String programCategory, int length,
                      ArrayList<Organization> orgContributors, ArrayList<Credit> credits) {
        this.name = name;
        this.id = id;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.programCategory = programCategory;
        this.length = length;
        this.orgContributors = orgContributors;
        this.credits = credits;
    }

    public Production(String name, Organization producer, String releaseDate, String programCategory, int length,
                      ArrayList<Organization> orgContributors, ArrayList<Credit> credits) {
        this.name = name;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.programCategory = programCategory;
        this.length = length;
        this.orgContributors = orgContributors;
        this.credits = credits;
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

    public String getProgramCategory() {
        return programCategory;
    }

    public void setProgramCategory(String programCategory) {
        this.programCategory = programCategory;
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
                "Produced by: " + producer.getName() + "\n\n" +
                "In collaboration with:\n");
        for (Organization organization : orgContributors) {
            returnString.append(organization.getName()).append("\n");
        }
        returnString.append("\n\n");
        for (Credit credit : credits) {
            StringBuilder playedBy = new StringBuilder(credit.getContributors().get(0).getName());
            for (int i = 1; i < credit.getContributors().size(); i++) {
                playedBy.append(", ").append(credit.getContributors().get(i).getName());
            }
            returnString.append(credit.getRole()).append("\t\t\t").append(playedBy);
        }
        return returnString.toString();
    }

    @Override
    public int store() {
        IDataFacade iDataFacade = new DataFacade();
        this.setId(iDataFacade.storeProductionData(this.name, this.releaseDate, this.length, this.producer.getId()));

        ArrayList<Integer> organizationIDs = new ArrayList<>();
        for(Organization organization : orgContributors) {
            organizationIDs.add(organization.getId());
        }
        Map<Integer, Set<Integer>> creditContributorIds = new HashMap<>();
        for(Credit credit : credits) {
            credit.store(this.getId());
            Set<Integer> contributorIds = new HashSet<>();
            for (Contributor contributor : credit.getContributors()) {
                contributorIds.add(contributor.getId());
            }
            creditContributorIds.put(credit.getId(), contributorIds);
        }

        iDataFacade.storeProductionCreditsOrganizations(organizationIDs, creditContributorIds, this.getId());
        return this.getId();
    }


}
