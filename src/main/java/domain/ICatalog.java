package domain;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

public interface ICatalog {
    static ICatalog getInstance() {
        return Catalog.getInstance();
    }

    boolean removeProduction(int productionID);

    Set<Production> searchForProductions(String searchString, int pageNumber, int pageSize);
    Set<Organization> searchForOrganizations(String searchStringer, int pageNumber, int pageSize);
    Set<Contributor> searchForContributors(String searchString, int pageNumber, int pageSize);

    void setDBurl(String url);
    void setDBPort(int port);
    void setDBName(String name);
    void setDBUsername(String username);
    void setDBPassword(String password);
    void initializeDatalayer();
}
