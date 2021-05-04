package data;

public class DataFacade implements IDataFacade{
    private OrganizationData orgData;

    public DataFacade () {
        orgData = new OrganizationData();
    }

    public boolean storeOrganization(String name) {
        return orgData.store(name);
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
