package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationData {
    private DBConnection dbConnection;

    public OrganizationData() {
        dbConnection = DBConnection.getInstance();
    }

    public int store(String name) {
        try {
            PreparedStatement insertStatement = dbConnection.prepareStatement(
                    "INSERT INTO organizations(name) VALUES (?) RETURNING id");
            insertStatement.setString(1, name);
            insertStatement.execute();
            return insertStatement.getResultSet().getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public boolean update() {
        return false;
    }

    public String materializeName(int contributorID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM organizations WHERE id = ?");
            stmt.setInt(1, contributorID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return null;
            }
            return sqlReturnValues.getString(2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
