package Acquaintance;

import java.util.ArrayList;
import java.util.Date;

public interface IContributor {
    String getName();
    int getId();
    Date getBirthDate();
    ArrayList<IProduction> getIProductions();
}
