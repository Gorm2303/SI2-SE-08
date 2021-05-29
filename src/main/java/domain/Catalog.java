package domain;

import data.DataFacade;
import data.IDataFacade;

import java.util.Set;
import java.util.TreeSet;


public class Catalog implements ICatalog{
    private IDataFacade dataFacade;
    private static ICatalog instance;

    private Catalog() {
        dataFacade = new DataFacade();
    }

    // Singleton design pattern
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
    public boolean removeProduction(int productionID){
        return dataFacade.deleteProduction(productionID);
    }

    @Override
    public Set<Production> searchForProductions(String searchString, int pageNumber, int pageSize) {
        Set<Production> productionSet = new TreeSet<>();
        for(Integer id : dataFacade.searchForProductions(searchString, pageNumber, pageSize)) {
            productionSet.add(Production.get(id));
        }
        return productionSet;
    }

    @Override
    public Set<Organization> searchForOrganizations(String searchString, int pageNumber, int pageSize) {
        Set<Organization> organizations = new TreeSet<>();
        for (int id : dataFacade.searchForOrganizations(searchString, pageNumber, pageSize)) {
            organizations.add(Organization.get(id));
        }
        return organizations;
    }

    @Override
    public Set<Contributor> searchForContributors(String searchString, int pageNumber, int pageSize) {
        Set<Contributor> contributors = new TreeSet<>();
        for (int id : dataFacade.searchForContributors(searchString, pageNumber, pageSize)) {
            contributors.add(Contributor.get(id));
        }
        return contributors;
    }
}
