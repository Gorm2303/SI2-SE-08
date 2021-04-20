package Acquaintance;

import domain.Credit;
import domain.Organization;
import domain.Production;

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

    static Production makeProduction(String name, int id, IOrganization producer, Date releaseDate,
                                     String programCategory, int length, ArrayList<IOrganization> orgContributors,
                                     ArrayList<ICredit> credits) {
        ArrayList<Organization> newOrganizations = new ArrayList<>();
        ArrayList<Credit> newCredits = new ArrayList<>();
        for (IOrganization organization : orgContributors) {
            newOrganizations.add(IOrganization.makeOrganization(organization.getName(), organization.getId()));
        }
        for (ICredit credit : credits) {
            newCredits.add(ICredit.makeCredit(credit.getRole(), credit.getIContributors()));
        }

        Organization newProducer = new Organization(producer.getName(), producer.getId());
        return new Production(name, id, newProducer, releaseDate, programCategory, length, newOrganizations, newCredits);
    }
}
