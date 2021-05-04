package data;

public class CreditData {
    private DBConnection dbConnection;

    public CreditData() {
        dbConnection = DBConnection.getInstance();
    }

    public boolean store() {
        return false;
    }

    public boolean update() {
        return false;
    }
}
