package data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public interface IDataFacade {

    int storeOrganization(String name);
    int storeContributor(String name, String birthDate);
    int storeCredit(String role, int productionID, Set<Integer> contributorIDs);
    int storeProductionData(String name, String releaseDate, int length, int producerID);
    void storeProductionCreditsOrganizations(ArrayList<Integer> organizationIDs, int productionID);


    String materializeOrganizationName(int contributorID);

    String materializeContributorName(int contributorID);
    String materializeContributorBirthDate(int contributorID);

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

    LinkedList<Integer> searchThroughOrganizations(String searchString);
    LinkedList<Integer> searchThroughContributors(String searchString);
    Set<Integer> searchForProductions(String searchString, int pageNumber, int pageSize);
}
