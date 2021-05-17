package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    public void storeCreditOrganizations(ArrayList<Integer> organizationIDs, int productionID) {
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

    public boolean update() {
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

    public ArrayList<Integer> materializeOrganizationIDs(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(
                    "SELECT organizationId FROM OrganizationsInProductions WHERE productionId = ?");
            stmt.setInt(1, productionID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            ArrayList<Integer> organizationIDs = new ArrayList<>();
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
            if (deleted == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
