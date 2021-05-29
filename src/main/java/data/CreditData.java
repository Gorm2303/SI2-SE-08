package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CreditData {
    private DBConnection dbConnection;

    public CreditData() {
        dbConnection = DBConnection.getInstance();
    }

    public int store(String role, int productionID, Set<Integer> contributorIDs) {
        try {
            // Store the credit object itself
            PreparedStatement insertStatement = dbConnection.prepareStatement(
                    "INSERT INTO credits(role, productionID) VALUES (?,?) RETURNING id");
            insertStatement.setString(1, role);
            insertStatement.setInt(2, productionID);
            insertStatement.execute();
            insertStatement.getResultSet().next();
            int creditID = insertStatement.getResultSet().getInt(1);

            //Store the contributor - credit relationship
            for (Integer contributorID : contributorIDs) {
                PreparedStatement organizationsStatement = dbConnection.prepareStatement(
                        "INSERT INTO ContributorsInCredits(creditId, contributorId) VALUES (?,?)"
                );
                organizationsStatement.setInt(1, creditID);
                organizationsStatement.setInt(2, contributorID);
                organizationsStatement.execute();
            }
            return creditID;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public String materializeRole(int creditID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT role FROM credits WHERE id = ?");
            stmt.setInt(1, creditID);
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

    // Get ID of the contributors that contributed/played to this credit/role
    public Set<Integer> materializeContributorIDs(int creditID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(
                    "SELECT contributorId FROM ContributorsInCredits WHERE creditId = ?");
            stmt.setInt(1, creditID);
            ResultSet sqlReturnValues = stmt.executeQuery();
            Set<Integer> contributorIDs = new HashSet<>();
            while(sqlReturnValues.next()) {
                contributorIDs.add(sqlReturnValues.getInt(1));
            }
            return contributorIDs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    // Delete the credits which are in the specified production. Due to CASCADE constraints in the database,
    // the relevant contributor - credit relationship is also deleted.
    public boolean deleteCreditsInProduction(int productionID) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(
                    "DELETE FROM credits WHERE productionId = (?)");
            stmt.setInt(1, productionID);
            int deleted = stmt.executeUpdate();
            return deleted != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
