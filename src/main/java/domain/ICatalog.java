package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public interface ICatalog {
    static ICatalog getInstance() {
        return Catalog.getInstance();
    }

    Production showProduction(int productionId);
    void addProduction(Production production);
    Production addProduction(String name, Organization producer, Date releaseDate, String programCategory, int length,
                       ArrayList<Organization> orgContributors, ArrayList<Credit> credits);
    void removeProduction(Production production);
    Contributor showContributor(int contributorId);
    Contributor addContributor(String name, Date birthDate);
    void removeContributor(int contributorId);
    Organization showOrganization(int organizationId);
    Organization addOrganization(String name);
    void removeOrganization(int organizationId);
    LinkedList<Organization> searchForOrganizations(String searchString, int pageNumber);
    LinkedList<Contributor> searchForContributors(String searchString, int pageNumber);

    void setDBurl(String url);
    void setDBPort(int port);
    void setDBName(String name);
    void setDBUsername(String username);
    void setDBPassword(String password);
    void initializeDatalayer();

    ArrayList<Production> getNext10Productions(int pageNumber);
}
