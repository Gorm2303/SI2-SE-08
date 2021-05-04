package data;

public class ContributorData {
    private DBConnection dbConnection;

    public ContributorData() {
        dbConnection = DBConnection.getInstance();
    }

    public boolean store() {
        return false;
    }

    public boolean update() {
        return false;
    }
}
