package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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

    public String materializeName(int contributorID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT name FROM organizations WHERE id = ?");
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

    // Searches for organization IDs where the search string matches the name of the contributor (not case sensitive)
    // Only returns a certain amount of id's depending on page size
    // Only returns part of the list of matches, depending on the page number
    public Set<Integer> searchFor(String searchString, int pageNumber, int pageSize) {
        Set<Integer> resultSet = new HashSet<>();
        int offset = pageSize * (pageNumber - 1);

        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT id FROM organizations WHERE name ILIKE (?) LIMIT " + pageSize + " OFFSET " + offset);
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

    // Gets a list of production names that the organization contributed to or produced
    public Set<String> materializeOrganizationIn(int id) {
        Set<String> contributesTo = new HashSet<>();
        try {
            // returns production IDs where the organization contributed to
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT productionid FROM organizationsinproductions WHERE organizationid = ?");
            stmt.setInt(1, id);
            ResultSet sqlReturnValues = stmt.executeQuery();
            LinkedList<Integer> productionIDs = new LinkedList<>();
            while (sqlReturnValues.next()) {
                productionIDs.add(sqlReturnValues.getInt(1));
            }

            // adds names of the found productions to the return list
            for (Integer pID : productionIDs) {
                stmt = dbConnection.prepareStatement("SELECT name FROM productions WHERE id = ?");
                stmt.setInt(1, pID);
                sqlReturnValues = stmt.executeQuery();
                sqlReturnValues.next();
                contributesTo.add(sqlReturnValues.getString(1));
            }

            // adds names of productions the organization produced, to the return list
            stmt = dbConnection.prepareStatement("SELECT name FROM productions WHERE producerid = ?");
            stmt.setInt(1, id);
            sqlReturnValues = stmt.executeQuery();
            while (sqlReturnValues.next()) {
                contributesTo.add(sqlReturnValues.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contributesTo;
    }

    // Deletes the organization - production (one to many) relationship, depending on the production ID
    public boolean deleteOrganizationsInProduction(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(
                    "DELETE FROM organizationsinproductions WHERE productionId = (?)");
            stmt.setInt(1, productionID);
            int deleted = stmt.executeUpdate();
            return deleted != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
