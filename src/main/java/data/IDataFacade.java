package data;

public interface IDataFacade {

    int storeOrganization(String name);
    int storeContributor(String name, String birthDate);
    int storeCredit(String role, int productionID);
    int storeProductionData(String name, String releaseDate, int length, int producerID);
    String materializeOrganizationName(int contributorID);

    //ArrayList<Integer> getNext10Productions(int pageNumber);
}
