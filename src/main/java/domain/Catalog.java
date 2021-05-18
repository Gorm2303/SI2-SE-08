package domain;

import data.DataFacade;
import data.IDataFacade;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;


public class Catalog implements ICatalog{
    private IDataFacade dataFacade;
    private static ICatalog instance;

    private Catalog() {
        dataFacade = new DataFacade();
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

    @Override
    public void removeProduction(int productionID){
        dataFacade.deleteProduction(productionID);
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
    public Set<Production> searchForProductions(String searchString, int pageNumber, int pageSize) {
        Set<Production> productionSet = new TreeSet<>();
        for(Integer id : dataFacade.searchForProductions(searchString, pageNumber, pageSize)) {
            productionSet.add(Production.get(id));
        }
        return productionSet;
    }
}
