package data;

import Acquaintance.IContributor;
import Acquaintance.IOrganization;
import Acquaintance.IProduction;

import java.util.ArrayList;

public class Data {
    private ArrayList<IProduction> productions;
    private ArrayList<IContributor> contributors;
    private ArrayList<IOrganization> organizations;

    public Data() {
        this.productions = new ArrayList<>();
        this.contributors = new ArrayList<>();
        this.organizations = new ArrayList<>();
    }

    public ArrayList<IProduction> getProductions() {
        return productions;
    }

    public ArrayList<IContributor> getContributors() {
        return contributors;
    }

    public ArrayList<IOrganization> getOrganizations() {
        return organizations;
    }
}
