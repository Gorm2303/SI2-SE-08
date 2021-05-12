package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

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

    public LinkedList<Integer> searchFor(String searchString) {
        LinkedList<Integer> resultList = new LinkedList<>();

        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT id FROM organizations WHERE name ILIKE (?)");
            stmt.setString(1, '%' + searchString + '%');
            ResultSet sqlReturnValues = stmt.executeQuery();

            while (sqlReturnValues.next()) {
                int id = sqlReturnValues.getInt(1);
                resultList.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
