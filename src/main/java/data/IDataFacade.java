package data;

public interface IDataFacade {

    int storeOrganization(String name);
    String materializeOrganizationName(int contributorID);

    //ArrayList<Integer> getNext10Productions(int pageNumber);
}
