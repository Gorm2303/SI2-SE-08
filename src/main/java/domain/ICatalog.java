package domain;

import java.util.LinkedList;
import java.util.Set;

public interface ICatalog {
    static ICatalog getInstance() {
        return Catalog.getInstance();
    }

    boolean removeProduction(int productionID);

    LinkedList<Organization> searchForOrganizations(String searchString, int pageNumber);
    LinkedList<Contributor> searchForContributors(String searchString, int pageNumber);
    Set<Production> searchForProductions(String searchString, int pageNumber, int pageSize);

    void setDBurl(String url);
    void setDBPort(int port);
    void setDBName(String name);
    void setDBUsername(String username);
    void setDBPassword(String password);
    void initializeDatalayer();
}
