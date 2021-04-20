package domain;

import java.util.ArrayList;

public class Credit {
    private String role;
    private ArrayList<Contributor> contributors;

    public Credit(String role, ArrayList<Contributor> contributors) {
        this.role = role;
        this.contributors = contributors;
    }

    public ArrayList<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(ArrayList<Contributor> contributors) {
        this.contributors = contributors;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
