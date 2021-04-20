package data;

import Acquaintance.IContributor;
import Acquaintance.IOrganization;
import Acquaintance.IProduction;

import java.util.ArrayList;

public interface IDataFacade {
    IProduction getProduction(int id);
    IOrganization getOrganization(int id);
    IContributor getContributor(int id);
    void addProduction(IProduction production);
    void addOrganization(IOrganization organization);
    void addContributor(IContributor contributor);
    void removeProduction(IProduction production);
    void removeOrganization(IOrganization organization);
    void removeContributor(IContributor contributor);
    int getProductionId();
    int getOrganizationId();
    int getContributorId();
    ArrayList<IProduction> getNext10Productions(int pageNumber);
}
