package data;

public class ProductionData {
    private DBConnection dbConnection;

    public ProductionData() {
        dbConnection = DBConnection.getInstance();
    }

    public boolean store() {
        return false;
    }

    public boolean update() {
        return false;
    }
}
