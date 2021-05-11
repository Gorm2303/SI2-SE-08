package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductionData {
    private DBConnection dbConnection;

    public ProductionData() {
        dbConnection = DBConnection.getInstance();
    }

    public int store(String name, String releaseDate, int length ,int producerID) {
        try {
            PreparedStatement insertStatement = dbConnection.prepareStatement(
                    "INSERT INTO productions(name, releaseDate, length, producerID) VALUES (?,?) RETURNING id");
            insertStatement.setString(1, name);
            insertStatement.setString(2, releaseDate);
            insertStatement.setInt(3, length);
            insertStatement.setInt(4, producerID);
            insertStatement.execute();
            insertStatement.getResultSet().next();
            return insertStatement.getResultSet().getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public boolean update() {
        return false;
    }

    public String materializeName(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM productions WHERE id = ?");
            stmt.setInt(1, productionID);
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
    public String materializeReleaseDate(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM productions WHERE id = ?");
            stmt.setInt(1, productionID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return null;
            }
            return sqlReturnValues.getString(3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public int materializeLength(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM productions WHERE id = ?");
            stmt.setInt(1, productionID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return 0;
            }
            return sqlReturnValues.getInt(4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public int materializeProducerID(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM productions WHERE id = ?");
            stmt.setInt(1, productionID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return 0;
            }
            return sqlReturnValues.getInt(5);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
