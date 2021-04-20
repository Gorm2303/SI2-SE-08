package Acquaintance;

import domain.Contributor;
import domain.Credit;

import java.util.ArrayList;

public interface ICredit {
    ArrayList<IContributor> getIContributors();
    String getRole();

    static Credit makeCredit(String role, ArrayList<IContributor> contributors) {
        ArrayList<Contributor> newContributors = new ArrayList<>();
        for (IContributor contributor : contributors) {
            newContributors.add(IContributor.makeContributor(contributor.getName(),
                    contributor.getId(),
                    contributor.getBirthDate()));
        }
        return new Credit(role, newContributors);
    }
}
