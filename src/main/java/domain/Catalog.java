package domain;

import Acquaintance.IProduction;
import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Catalog {
    private List<Production> productionList = new ArrayList<>();
    private List<Contributor> contributorList = new ArrayList<>();
    private List<Organization> organizationList = new ArrayList<>();
    private IDataFacade dataFacade;

    public Catalog() {
        dataFacade = new DataFacade();
    }


    public Production showProduction(int productionId){
        return null;
    }

    public void addProduction(String name, Organization producer, Date releaseDate, String programCategory, int length,
                              ArrayList<Organization> orgContributors, ArrayList<Credit> credits){
        Production productionToAdd = new Production(name, dataFacade.getProductionId(), producer, releaseDate,
                programCategory, length, orgContributors, credits);
        productionList.add(productionToAdd);
        dataFacade.addProduction(productionToAdd);
    }

    public void removeProduction(int productionId){

    }

    public Contributor showContributor(int contributorId){
        return null;
    }

    public void addContributor(String name, int id, Date birthDate){

    }

    public void removeContributor(int contributorId){

    }

    public Organization showOrganization(int organizationId){
        return null;
    }

    public void addOrganization(String name, int id){

    }

    public void removeOrganization(int organizationId){

    }
}
