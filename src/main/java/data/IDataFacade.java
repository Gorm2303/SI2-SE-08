package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public interface IDataFacade {
    // Store objects and their relationships in the database
    int storeOrganization(String name);
    int storeContributor(String name, String birthDate);
    int storeCredit(String role, int productionID, Set<Integer> contributorIDs);
    int storeProductionData(String name, String releaseDate, int length, int producerID);
    void storeProductionOrganizations(ArrayList<Integer> organizationIDs, int productionID);

    // Update the basic attributes of a production. Updating the list attributes is done in the domain layer
    boolean updateProductionSimpleData(int id, String name, String releaseDate, int length, int producerID);

    // Delete production objects and the relationships
    boolean deleteProduction(int productionID);
    boolean deleteOrganizationInProduction(int productionID);
    boolean deleteCreditsInProduction(int productionID);

    // Organization materialization methods
    String materializeOrganizationName(int organizationID);
    Set<String> materializeOrganizationIn(int organizationID);

    // Contributor materialization methods
    String materializeContributorName(int contributorID);
    String materializeContributorBirthDate(int contributorID);
    Set<String> materializeContributorIn(int contributorID);

    // Credit materialization methods
    String materializeCreditRole(int creditID);
    Set<Integer> materializeContributorIDs(int creditID);

    // Production materialization methods
    String materializeProductionName(int productionID);
    String materializeProductionReleaseDate(int productionID);
    int materializeProductionLength(int productionID);
    int materializeProductionProducerID(int productionID);
    Set<Integer> materializeProductionOrganizationIDs(int productionID);
    Set<Integer> materializeProductionCreditIDs(int productionID);

    // Database setup
    void setDBurl(String url);
    void setDBPort(int port);
    void setDBName(String name);
    void setDBUsername(String username);
    void setDBPassword(String password);
    void initializeDatabase();

    // Search methods
    Set<Integer> searchForProductions(String searchString, int pageNumber, int pageSize);
    Set<Integer> searchForOrganizations(String searchString, int pageNumber, int pageSize);
    Set<Integer> searchForContributors(String searchString, int pageNumber, int pageSize);

}
