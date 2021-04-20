package data;

import Acquaintance.IContributor;
import Acquaintance.IOrganization;
import Acquaintance.IProduction;
import domain.Production;

import java.util.ArrayList;

public class DataFacade implements IDataFacade{
    private Data data;

    public DataFacade () {
        this.data = new Data();
    }

    @Override
    public IProduction getProduction(int id) {
        for (IProduction production : data.getProductions()) {
            if (production.getId() == id) {
                return production;
            }
        }
        return null;
    }

    @Override
    public IOrganization getOrganization(int id) {
        for (IOrganization organization : data.getOrganizations()) {
            if (organization.getId() == id) {
                return organization;
            }
        }
        return null;
    }

    @Override
    public IContributor getContributor(int id) {
        for (IContributor contributor : data.getContributors()) {
            if (contributor.getId() == id) {
                return contributor;
            }
        }
        return null;
    }

    @Override
    public void addProduction(IProduction production) {
        data.getProductions().add(production);
    }

    @Override
    public void addOrganization(IOrganization organization) {
        data.getOrganizations().add(organization);
    }

    @Override
    public void addContributor(IContributor contributor) {
        data.getContributors().add(contributor);
    }

    @Override
    public void removeProduction(IProduction production) {
        data.getProductions().remove(production);
    }

    @Override
    public void removeOrganization(IOrganization organization) {
        data.getOrganizations().remove(organization);
    }

    @Override
    public void removeContributor(IContributor contributor) {
        data.getContributors().remove(contributor);
    }

    @Override
    public int getProductionId() {
        return IDManager.getProductionId();
    }

    @Override
    public int getOrganizationId() {
        return IDManager.getOrganizationId();
    }

    @Override
    public int getContributorId() {
        return IDManager.getContributorId();
    }


}
