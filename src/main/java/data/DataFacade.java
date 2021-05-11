package data;

public class DataFacade implements IDataFacade{
    private OrganizationData orgData;
    private ContributorData contributorData;
    private CreditData creditData;
    private ProductionData productionData;

    public DataFacade () {
        orgData = new OrganizationData();
    }
    @Override
    public int storeOrganization(String name) {
        return orgData.store(name);
    }

    @Override
    public String materializeOrganizationName(int contributorID) {
        return orgData.materializeName(contributorID);
    }

    @Override
    public int storeContributor(String name, String birthDate) {
        return contributorData.store(name, birthDate);
    }

    @Override
    public int storeCredit(String role, int productionID) {
        return creditData.store(role, productionID);
    }

    @Override
    public int storeProductionData(String name, String releaseDate, int length, int producerID) {
        return productionData.store(name, releaseDate, length, producerID);
    }

    /*
    @Override
    public ArrayList<Integer> getNext10Productions(int pageNumber) {
        ArrayList<Integer> returnList = new ArrayList<>();
        if (data.getProductions().size() < pageNumber * 10) {
            for (int i = (pageNumber - 1) * 10; i < data.getProductions().size(); i++) {
                returnList.add(data.getProductions().get(i));
            }
        }
        else {
            for (int i = (pageNumber - 1) * 10; i < pageNumber * 10; i++) {
                returnList.add(data.getProductions().get(i));
            }
        }
        return returnList;

    }

     */


}
