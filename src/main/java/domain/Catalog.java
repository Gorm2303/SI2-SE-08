package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Catalog {
    private List<Production> productionList = new ArrayList<>();
    private List<Contributor> contributorList = new ArrayList<>();
    private List<Organization> organizationList = new ArrayList<>();


    public Production showProduction(int productionId){
        return null;
    }

    public void addProduction(String name, int id, Organization producer, Date releaseDate, String programCategory, int length){

    }

    public void removeProduction(int productionId){

    }

    public Contributor showContributor(int contributorId){
        return null;
    }

    public void addContributor(String name, int id, Date birthDate){

    }

    public void removeContributor(int contributorId){

    }

    public Organization showOrganization(int organizationId){
        return null;
    }

    public void addOrganization(String name, int id){

    }

    public void removeOrganization(int organizationId){

    }
}
