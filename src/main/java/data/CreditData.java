package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditData {
    private DBConnection dbConnection;

    public CreditData() {
        dbConnection = DBConnection.getInstance();
    }

    public int store(String role, int productionID) {
        try {
            PreparedStatement insertStatement = dbConnection.prepareStatement(
                    "INSERT INTO credits(role, productionID) VALUES (?,?) RETURNING id");
            insertStatement.setString(1, role);
            insertStatement.setInt(2, productionID);
            insertStatement.execute();
            insertStatement.getResultSet().next();
            return insertStatement.getResultSet().getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }
    public String materializeRole(int creditID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM credits WHERE id = ?");
            stmt.setInt(1, creditID);
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
    public int materializeProductionID(int creditID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM credits WHERE id = ?");
            stmt.setInt(1, creditID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return 0;
            }
            return sqlReturnValues.getInt(3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public boolean update() {
        return false;
    }
}
