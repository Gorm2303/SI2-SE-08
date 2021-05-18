package data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class DataFacade implements IDataFacade{
    private OrganizationData orgData;
    private ContributorData contributorData;
    private CreditData creditData;
    private ProductionData productionData;
    private DBConnection dbConnection;

    public DataFacade () {
        orgData = new OrganizationData();
        contributorData = new ContributorData();
        creditData = new CreditData();
        productionData = new ProductionData();
        dbConnection = DBConnection.getInstance();
    }



    @Override
    public void setDBurl(String url) {
        dbConnection.setUrl(url);
    }

    @Override
    public void setDBPort(int port) {
        dbConnection.setPort(port);
    }

    @Override
    public void setDBName(String name) {
        dbConnection.setDatabaseName(name);
    }

    @Override
    public void setDBUsername(String username) {
        dbConnection.setUsername(username);
    }

    @Override
    public void setDBPassword(String password) {
        dbConnection.setPassword(password);
    }

    @Override
    public void initializeDatabase() {
        dbConnection.initializePostgresqlDatabase();
    }
  
  
  
    @Override
    public LinkedList<Integer> searchThroughOrganizations(String searchString) {
        return orgData.searchFor(searchString);
    }

    @Override
    public LinkedList<Integer> searchThroughContributors(String searchString) {
        return contributorData.searchFor(searchString);
    }

    @Override
    public Set<Integer> searchForProductions(String searchString, int pageNumber, int pageSize) {
        return productionData.searchFor(searchString, pageNumber, pageSize);
    }



    @Override
    public int storeOrganization(String name) {
        return orgData.store(name);
    }

    @Override
    public int storeContributor(String name, String birthDate) {
        return contributorData.store(name, birthDate);
    }

    @Override
    public int storeCredit(String role, int productionID, Set<Integer> contributorIDs) {
        return creditData.store(role, productionID, contributorIDs);
    }

    @Override
    public int storeProductionData(String name, String releaseDate, int length, int producerID) {
        return productionData.store(name, releaseDate, length, producerID);
    }

    @Override
    public void storeProductionOrganizations(ArrayList<Integer> organizationIDs, int productionID) {
        productionData.storeOrganizations(organizationIDs, productionID);
    }


    @Override
    public boolean updateProductionSimpleData(int id, String name, String releaseDate, int length, int producerID) {
        return productionData.updateSimpleValues(id, name, releaseDate, length, producerID);
    }


    @Override
    public boolean deleteProduction(int productionID) {
        return productionData.delete(productionID);
    }

    @Override
    public boolean deleteOrganizationInProduction(int productionID) {
        return orgData.deleteOrganizationsInProduction(productionID);
    }

    @Override
    public boolean deleteCreditsInProduction(int productionID) {
        return creditData.deleteCreditsInProduction(productionID);
    }


    @Override
    public String materializeOrganizationName(int contributorID) {
        return orgData.materializeName(contributorID);
    }

    @Override
    public String materializeContributorName(int contributorID) {
        return contributorData.materializeName(contributorID);
    }

    @Override
    public String materializeContributorBirthDate(int contributorID) {
        return contributorData.materializeBirthDate(contributorID);
    }

    @Override
    public String materializeCreditRole(int creditID) {
        return creditData.materializeRole(creditID);
    }

    @Override
    public int materializeCreditProductionID(int creditID) {
        return creditData.materializeProductionID(creditID);
    }

    public Set<Integer> materializeContributorIDs(int creditID) {
        return creditData.materializeContributorIDs(creditID);
    }

    @Override
    public String materializeProductionName(int productionID) {
        return productionData.materializeName(productionID);
    }

    @Override
    public String materializeProductionReleaseDate(int productionID) {
        return productionData.materializeReleaseDate(productionID);
    }

    @Override
    public int materializeProductionLength(int productionID) {
        return productionData.materializeLength(productionID);
    }

    @Override
    public int materializeProductionProducerID(int productionID) {
        return productionData.materializeProducerID(productionID);
    }

    @Override
    public ArrayList<Integer> materializeProductionOrganizationIDs(int productionID) {
        return productionData.materializeOrganizationIDs(productionID);
    }

    @Override
    public Set<Integer> materializeProductionCreditIDs(int productionID) {
        return productionData.materializeCreditIDs(productionID);
    }


}
