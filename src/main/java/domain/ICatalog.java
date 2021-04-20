package domain;

import java.util.ArrayList;
import java.util.Date;

public interface ICatalog {
    Production showProduction(int productionId);
    void addProduction(String name, Organization producer, Date releaseDate, String programCategory, int length,
                       ArrayList<Organization> orgContributors, ArrayList<Credit> credits);
    void removeProduction(int productionId);
    Contributor showContributor(int contributorId);
    void addContributor(String name, int id, Date birthDate);
    void removeContributor(int contributorId);
    Organization showOrganization(int organizationId);
    void addOrganization(String name, int id);
    void removeOrganization(int organizationId);

    ArrayList<Production> getNext10Productions(int pageNumber);
}
