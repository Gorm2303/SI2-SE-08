package domain;

import Acquaintance.IProduction;
import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;
import java.util.Date;

public class Catalog implements ICatalog{
    private ArrayList<Production> next10productions;
    //private List<Contributor> contributorList = new ArrayList<>();
    //private List<Organization> organizationList = new ArrayList<>();
    private IDataFacade dataFacade;
    private static Catalog instance;

    private Catalog() {
        dataFacade = new DataFacade();
        next10productions = new ArrayList<>();
    }

    public static Catalog getInstance(){
        if (instance == null) {
            instance = new Catalog();
        }
        return instance;
    }

    public Production showProduction(int productionId){
        return null;
    }

    public void addProduction(Production production) {
        dataFacade.addProduction(production);
    }

    public Production addProduction(String name, Organization producer, Date releaseDate, String programCategory, int length,
                              ArrayList<Organization> orgContributors, ArrayList<Credit> credits){
        Production productionToAdd = new Production(name, dataFacade.getProductionId(), producer, releaseDate,
                programCategory, length, orgContributors, credits);
        dataFacade.addProduction(productionToAdd);
        return productionToAdd;
    }

    @Override
    public void removeProduction(Production production){
        dataFacade.removeProduction(production);
    }

    public Contributor showContributor(int contributorId){
        return null;
    }

    public Contributor addContributor(String name, Date birthDate){
        Contributor contributorToAdd = new Contributor(name, dataFacade.getContributorId(), birthDate);
        dataFacade.addContributor(contributorToAdd);
        return contributorToAdd;
    }

    public void removeContributor(int contributorId){

    }

    public Organization showOrganization(int organizationId){
        return null;
    }

    public Organization addOrganization(String name){
        Organization organizationToAdd = new Organization(name, dataFacade.getOrganizationId());
        dataFacade.addOrganization(organizationToAdd);
        return organizationToAdd;
    }

    public void removeOrganization(int organizationId){

    }

    @Override
    public ArrayList<Production> getNext10Productions(int pageNumber) {
        ArrayList<IProduction> temp10 = dataFacade.getNext10Productions(pageNumber);
        ArrayList<Production> returnList = new ArrayList<>();
        for (IProduction production : temp10) {
            returnList.add((Production) production);
        }
        return returnList;
    }
}
