package data;

import Acquaintance.IContributor;
import Acquaintance.IOrganization;
import Acquaintance.IProduction;
import domain.Production;

public class DataFacade {
    private Data data;
    private IDManager idManager;

    public DataFacade () {
        this.data = new Data();
        this.idManager = new IDManager();
    }

    public IProduction getProduction(int id) {
        for (IProduction production : data.getProductions()) {
            if (production.getId() == id) {
                return production;
            }
        }
        return null;
    }

    public IOrganization getOrganization(int id) {
        for (IOrganization organization : data.getOrganizations()) {
            if (organization.getId() == id) {
                return organization;
            }
        }
        return null;
    }

    public IContributor getContributor(int id) {
        for (IContributor contributor : data.getContributors()) {
            if (contributor.getId() == id) {
                return contributor;
            }
        }
        return null;
    }

    public void addProduction(IProduction production) {
        data.getProductions().add(production);
    }

    public void addOrganization(IOrganization organization) {
        data.getOrganizations().add(organization);
    }

    public void addContributor(IContributor contributor) {
        data.getContributors().add(contributor);
    }

    public void removeProduction(IProduction production) {
        data.getProductions().remove(production);
    }

    public void removeOrganization(IOrganization organization) {
        data.getOrganizations().remove(organization);
    }

    public void removeContributor(IContributor contributor) {
        data.getContributors().remove(contributor);
    }


}
