package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContributorData {
    private DBConnection dbConnection;

    public ContributorData() {
        dbConnection = DBConnection.getInstance();
    }

    public int store(String name, String birthDate) {
        try {
            PreparedStatement insertStatement = dbConnection.prepareStatement(
                    "INSERT INTO contributors(name, birthDay) VALUES (?,?) RETURNING id");
            insertStatement.setString(1, name);
            insertStatement.setString(2, birthDate);
            insertStatement.execute();
            insertStatement.getResultSet().next();
            return insertStatement.getResultSet().getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }
    public String materializeName(int contributorID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM contributors WHERE id = ?");
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
    public String materializeBirthDate(int contributorID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM contributors WHERE id = ?");
            stmt.setInt(1, contributorID);
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
    public boolean update() {
        return false;
    }
}
