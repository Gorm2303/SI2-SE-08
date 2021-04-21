package domain;

import java.util.ArrayList;
import java.util.Date;

public interface ICatalog {
    Production showProduction(int productionId);
    public void addProduction(Production production);
    Production addProduction(String name, Organization producer, Date releaseDate, String programCategory, int length,
                       ArrayList<Organization> orgContributors, ArrayList<Credit> credits);
    void removeProduction(int productionId);
    Contributor showContributor(int contributorId);
    Contributor addContributor(String name, Date birthDate);
    void removeContributor(int contributorId);
    Organization showOrganization(int organizationId);
    Organization addOrganization(String name);
    void removeOrganization(int organizationId);

    ArrayList<Production> getNext10Productions(int pageNumber);
}
