package Acquaintance;

import domain.Contributor;

import java.util.ArrayList;
import java.util.Date;

public interface IContributor {
    String getName();
    int getId();
    Date getBirthDate();
    //ArrayList<IProduction> getIProductions(); //This can be implemented later, but for now it is only causing problems
    static Contributor makeContributor(String name, int id, Date birthDate) {
        return new Contributor(name, id, birthDate);
    }
}
