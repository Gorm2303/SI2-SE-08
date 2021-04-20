package data;

public class IDManager {
    static private int productionId = 1;
    static private int organizationId = 1;
    static private int contributorId = 1;

    public static int getProductionId() {
        return productionId++;
    }

    public static int getOrganizationId() {
        return organizationId++;
    }

    public static int getContributorId() {
        return contributorId++;
    }
}
