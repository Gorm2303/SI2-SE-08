package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public interface IDataFacade {

    int storeOrganization(String name);
    int storeContributor(String name, String birthDate);
    int storeCredit(String role, int productionID, Set<Integer> contributorIDs);
    int storeProductionData(String name, String releaseDate, int length, int producerID);
    void storeProductionOrganizations(ArrayList<Integer> organizationIDs, int productionID);

    boolean updateProductionSimpleData(int id, String name, String releaseDate, int length, int producerID);

    boolean deleteProduction(int productionID);
    boolean deleteOrganizationInProduction(int productionID);
    boolean deleteCreditsInProduction(int productionID);

    String materializeOrganizationName(int organizationID);
    Set<String> materializeOrganizationIn(int organizationID);

    String materializeContributorName(int contributorID);
    String materializeContributorBirthDate(int contributorID);
    Set<String> materializeContributorIn(int contributorID);

    String materializeCreditRole(int creditID);
    int materializeCreditProductionID(int creditID);
    Set<Integer> materializeContributorIDs(int creditID);

    String materializeProductionName(int productionID);
    String materializeProductionReleaseDate(int productionID);
    int materializeProductionLength(int productionID);
    int materializeProductionProducerID(int productionID);
    ArrayList<Integer> materializeProductionOrganizationIDs(int productionID);
    Set<Integer> materializeProductionCreditIDs(int productionID);


    void setDBurl(String url);
    void setDBPort(int port);
    void setDBName(String name);
    void setDBUsername(String username);
    void setDBPassword(String password);
    void initializeDatabase();

    Set<Integer> searchForProductions(String searchString, int pageNumber, int pageSize);
    Set<Integer> searchForOrganizations(String searchString, int pageNumber, int pageSize);
    Set<Integer> searchForContributors(String searchString, int pageNumber, int pageSize);

}
