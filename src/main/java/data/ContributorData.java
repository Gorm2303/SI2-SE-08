package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class ContributorData {
    private DBConnection dbConnection;

    public ContributorData() {
        dbConnection = DBConnection.getInstance();
    }

    public int store(String name, String birthDate) {
        try {
            PreparedStatement insertStatement = dbConnection.prepareStatement(
                    "INSERT INTO contributors(name, birthDate) VALUES (?,?) RETURNING id");
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
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT name FROM contributors WHERE id = ?");
            stmt.setInt(1, contributorID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return null;
            }
            return sqlReturnValues.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public String materializeBirthDate(int contributorID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT birthDate FROM contributors WHERE id = ?");
            stmt.setInt(1, contributorID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return null;
            }
            return sqlReturnValues.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public HashSet<String> materializeContributorIn(int contributorID) {
        HashSet<String> contributesTo = new HashSet<>();
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT creditid FROM contributorsincredits WHERE contributorid = ?");
            stmt.setInt(1, contributorID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            LinkedList<Integer> temp = new LinkedList<>();
            while (sqlReturnValues.next()) {
                temp.add(sqlReturnValues.getInt(1));
            }

            for (Integer i : temp) {
                PreparedStatement statement = dbConnection.prepareStatement("SELECT productionid FROM credits WHERE id = ?");
                statement.setInt(1, i);
                ResultSet sqlReturns = statement.executeQuery();
                PreparedStatement state = dbConnection.prepareStatement("SELECT name FROM productions WHERE id = ?");
                sqlReturns.next();
                state.setInt(1, sqlReturns.getInt(1));
                ResultSet sqlNames = state.executeQuery();
                sqlNames.next();
                contributesTo.add(sqlNames.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contributesTo;
    }

    public Set<Integer> searchFor(String searchString, int pageNumber, int pageSize) {
        Set<Integer> resultSet = new HashSet<>();
        int offset = pageSize * (pageNumber - 1);

        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT id FROM contributors WHERE name ILIKE (?) LIMIT " + pageSize + " OFFSET " + offset);
            stmt.setString(1, '%' + searchString + '%');
            ResultSet sqlReturnValues = stmt.executeQuery();

            while (sqlReturnValues.next()) {
                int id = sqlReturnValues.getInt(1);
                resultSet.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
