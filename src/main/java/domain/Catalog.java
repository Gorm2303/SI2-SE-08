package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class Catalog implements ICatalog{
    private ArrayList<Production> next10productions;
    private IDataFacade dataFacade;
    private static ICatalog instance;

    private Catalog() {
        dataFacade = new DataFacade();
        next10productions = new ArrayList<>();
    }

    public static ICatalog getInstance(){
        if (instance == null) {
            instance = new Catalog();
        }
        return instance;
    }

    public void setDBurl(String url) {
        dataFacade.setDBurl(url);
    }

    public void setDBPort(int port) {
        dataFacade.setDBPort(port);
    }

    public void setDBName(String name) {
        dataFacade.setDBName(name);
    }

    public void setDBUsername(String username) {
        dataFacade.setDBUsername(username);
    }
    public void setDBPassword(String password) {
        dataFacade.setDBPassword(password);
    }

    public void initializeDatalayer() {
        dataFacade.initializeDatabase();
    }


    public Production showProduction(int productionId){
        return null;
    }

    public void addProduction(Production production) {
        //dataFacade.addProduction(production);
    }

    public Production addProduction(String name, Organization producer, Date releaseDate, String programCategory, int length,
                              ArrayList<Organization> orgContributors, ArrayList<Credit> credits){
        //Production productionToAdd = new Production(name, dataFacade.getProductionId(), producer, releaseDate,
                //programCategory, length, orgContributors, credits);
        //dataFacade.addProduction(productionToAdd);
        return null; //productionToAdd;
    }

    @Override
    public void removeProduction(Production production){
        //dataFacade.removeProduction(production);
    }

    public Contributor showContributor(int contributorId){
        return null;
    }

    public Contributor addContributor(String name, Date birthDate){
        //Contributor contributorToAdd = new Contributor(name, dataFacade.getContributorId(), birthDate);
        //dataFacade.addContributor(contributorToAdd);
        return null;//contributorToAdd;
    }

    public void removeContributor(int contributorId){

    }

    public Organization showOrganization(int organizationId){
        return null;
    }

    public Organization addOrganization(String name){
        //Organization organizationToAdd = new Organization(name, dataFacade.getOrganizationId());
        //dataFacade.addOrganization(organizationToAdd);
        return null; //organizationToAdd;
    }

    public void removeOrganization(int organizationId){

    }

    @Override
    public LinkedList<Organization> searchForOrganizations(String searchString, int pageNumber) {
        LinkedList<Organization> organizations = new LinkedList<>();
        for (int i : dataFacade.searchThroughOrganizations(searchString)) {
            organizations.add(Organization.get(i));
        }
        return organizations;
    }

    @Override
    public LinkedList<Contributor> searchForContributors(String searchString, int pageNumber) {
        LinkedList<Contributor> contributors = new LinkedList<>();
        for (int i : dataFacade.searchThroughContributors(searchString)) {
            contributors.add(Contributor.get(i));
        }
        return contributors;
    }

    @Override
    public ArrayList<Production> getNext10Productions(int pageNumber) {
        /*
        ArrayList<IProduction> temp10 = dataFacade.getNext10Productions(pageNumber);
        ArrayList<Production> returnList = new ArrayList<>();
        for (IProduction production : temp10) {
            returnList.add((Production) production);
        }
        return returnList;
         */
        return new ArrayList<>();
    }
}
