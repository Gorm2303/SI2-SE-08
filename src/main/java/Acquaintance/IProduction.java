package Acquaintance;

import java.util.ArrayList;
import java.util.Date;

public interface IProduction {
    String getName();
    int getId();
    ArrayList<IOrganization> getIOrganizations();
    ArrayList<ICredit> getICredits();
    IOrganization getIProducer();
    Date getReleaseDate();
    String getProgramCategory();
    int getLength();
}
