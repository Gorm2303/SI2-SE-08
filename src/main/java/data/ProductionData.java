package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ProductionData {
    private DBConnection dbConnection;

    public ProductionData() {
        dbConnection = DBConnection.getInstance();
    }

    public int store(String name, String releaseDate, int length , int producerID) {
        try {
            PreparedStatement insertStatement = dbConnection.prepareStatement(
                    "INSERT INTO productions(name, releaseDate, length, producerID) VALUES (?, ?, ?, ?) RETURNING id");
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

    // Stores the production - organization (one to many) relationship
    public void storeOrganizations(ArrayList<Integer> organizationIDs, int productionID) {
        try {
            for (Integer id : organizationIDs) {
                PreparedStatement organizationsStatement = dbConnection.prepareStatement(
                        "INSERT INTO OrganizationsInProductions(productionId, organizationId) VALUES (?,?)"
                );
                organizationsStatement.setInt(1, productionID);
                organizationsStatement.setInt(2, id);
                organizationsStatement.execute();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Updates the simple values of the production (producer, name, release date, length)
    public boolean updateSimpleValues(int productionID, String name, String releaseDate, int length , int producerID) {
        try {
            PreparedStatement updateStatement = dbConnection.prepareStatement(
                    "UPDATE productions SET name = ?, releaseDate = ?, length = ?, producerID = ? WHERE id = ?");
            updateStatement.setString(1, name);
            updateStatement.setString(2, releaseDate);
            updateStatement.setInt(3, length);
            updateStatement.setInt(4, producerID);
            updateStatement.setInt(5, productionID);
            updateStatement.execute();
            int changed = updateStatement.executeUpdate();
            return changed != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public String materializeName(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT name FROM productions WHERE id = ?");
            stmt.setInt(1, productionID);
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
    public String materializeReleaseDate(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT releaseDate FROM productions WHERE id = ?");
            stmt.setInt(1, productionID);
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
    public int materializeLength(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT length FROM productions WHERE id = ?");
            stmt.setInt(1, productionID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return 0;
            }
            return sqlReturnValues.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public int materializeProducerID(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT producerId FROM productions WHERE id = ?");
            stmt.setInt(1, productionID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return 0;
            }
            return sqlReturnValues.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public Set<Integer> materializeOrganizationIDs(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(
                    "SELECT organizationId FROM OrganizationsInProductions WHERE productionId = ?");
            stmt.setInt(1, productionID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            Set<Integer> organizationIDs = new HashSet<>();
            while(sqlReturnValues.next()) {
                organizationIDs.add(sqlReturnValues.getInt(1));
            }
            return organizationIDs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Set<Integer> materializeCreditIDs(int productionID) {
        try {
            PreparedStatement creditStatement = dbConnection.prepareStatement(
                    "SELECT id FROM Credits WHERE productionId = ?");
            creditStatement.setInt(1, productionID);
            ResultSet result = creditStatement.executeQuery();

            Set<Integer> creditIDs = new HashSet<>();
            while(result.next()) {
                creditIDs.add(result.getInt(1));
            }
            return creditIDs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    // Searches for production IDs where the search string matches the name of the contributor (not case sensitive)
    // Only returns a certain amount of id's depending on page size
    // Only returns part of the list of matches, depending on the page number
    public Set<Integer> searchFor(String searchString, int pageNumber, int pageSize) {
        Set<Integer> resultSet = new HashSet<>();
        int offset = pageSize * (pageNumber - 1);
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT id FROM productions WHERE name ILIKE (?) LIMIT " + pageSize + " OFFSET " + offset);
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

    public boolean delete(int id) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("DELETE FROM productions WHERE id = (?)");
            stmt.setInt(1, id);
            int deleted = stmt.executeUpdate();
            return deleted != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
