package data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrganizationData {
    private DBConnection dbConnection;

    public OrganizationData() {
        dbConnection = DBConnection.getInstance();
    }

    public boolean store(String name) {
        try {
            PreparedStatement insertStatement = dbConnection.prepareStatement(
                    "INSERT INTO organizations(name) VALUES (?)");
            insertStatement.setString(1, name);
            insertStatement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean update() {
        return false;
    }
}
