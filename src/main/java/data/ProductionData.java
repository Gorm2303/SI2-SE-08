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

    public void storeCreditOrganizations(ArrayList<Integer> organizationIDs, Map<Integer,
            Set<Integer>> creditContributorIDs, int productionID) {
        System.out.println("IT IS HERE");
        try {
            for (Integer id : organizationIDs) {
                PreparedStatement organizationsStatement = dbConnection.prepareStatement(
                        "INSERT INTO OrganizationsInProductions(productionId, organizationId) VALUES (?,?)"
                );
                organizationsStatement.setInt(1, productionID);
                organizationsStatement.setInt(2, id);
                organizationsStatement.execute();
            }

            for (Map.Entry<Integer, Set<Integer>> entry : creditContributorIDs.entrySet()) {
                for (Integer contributorID : entry.getValue()) {
                    PreparedStatement organizationsStatement = dbConnection.prepareStatement(
                            "INSERT INTO ContributorsInCredits(creditId, contributorId) VALUES (?,?)"
                    );
                    organizationsStatement.setInt(1, entry.getKey());
                    organizationsStatement.setInt(2, contributorID);
                    organizationsStatement.execute();
                }
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

    public Map<Integer, Set<Integer>> materializeCredits(int productionID) {
        try {
            PreparedStatement creditStatement = dbConnection.prepareStatement(
                    "SELECT id FROM Credits WHERE productionId = ?");
            creditStatement.setInt(1, productionID);
            ResultSet creditIds = creditStatement.executeQuery();
            Map<Integer, Set<Integer>> creditsContributorsIDs = new HashMap<>();
            while(creditIds.next()) {
                PreparedStatement contributorStatement = dbConnection.prepareStatement(
                        "SELECT contributorId FROM ContributorsInCredits WHERE creditId = ?");
                contributorStatement.setInt(1, creditIds.getInt(1));
                ResultSet contributorIds = contributorStatement.executeQuery();
                Set<Integer> contributorSet = new HashSet<>();
                while(contributorIds.next()) {
                    contributorSet.add(contributorIds.getInt(1));
                }
                creditsContributorsIDs.put(creditIds.getInt(1), contributorSet);
            }

            return creditsContributorsIDs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
