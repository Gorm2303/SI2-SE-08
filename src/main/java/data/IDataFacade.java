package data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public interface IDataFacade {

    int storeOrganization(String name);
    int storeContributor(String name, String birthDate);
    int storeCredit(String role, int productionID);
    int storeProductionData(String name, String releaseDate, int length, int producerID);
    void storeProductionCreditsOrganizations(ArrayList<Integer> organizationIDs, Map<Integer,
            Set<Integer>> creditContributorIDs, int productionID);
    String materializeOrganizationName(int contributorID);
    String materializeContributorName(int contributorID);
    String materialiseContributorBirthDate(int contributorID);
    LinkedList<Integer> searchThroughOrganizations(String searchString);

    //ArrayList<Integer> getNext10Productions(int pageNumber);
}
