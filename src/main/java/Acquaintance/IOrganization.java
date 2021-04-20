package Acquaintance;

import domain.Organization;

import java.util.ArrayList;

public interface IOrganization {
    String getName();
    int getId();
    //ArrayList<IProduction> getIProductions(); //This can be implemented later, but for now it is only causing problems
    static Organization makeOrganization(String name, int id) {
        return new Organization(name, id);
    }
}
