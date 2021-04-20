package domain;

import Acquaintance.IContributor;
import Acquaintance.ICredit;
import Acquaintance.IOrganization;
import Acquaintance.IProduction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Production implements IProduction {
    private String name;
    private int id;
    private Organization producer;
    private Date releaseDate;
    private String programCategory;
    private int length;
    private ArrayList<Organization> orgContributors;
    private ArrayList<Credit> credits;

    public Production() {
    }

    public Production(String name, int id, Organization producer, Date releaseDate, String programCategory, int length,
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
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
    public ArrayList<IOrganization> getIOrganizations() {
        return new ArrayList<>(orgContributors);
    }

    @Override
    public ArrayList<ICredit> getICredits() {
        return new ArrayList<>(credits);
    }

    @Override
    public IOrganization getIProducer() {
        return producer;
    }
}
