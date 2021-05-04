package domain;

import java.util.ArrayList;
import java.util.Date;

public class Contributor implements Savable {
    private String name;
    private int id;
    private Date birthDate;
    private ArrayList<Production> productionsIsIn;

    public Contributor() {}

    public Contributor(String name, int id, Date birthDate) {
        this.name = name;
        this.id = id;
        this.birthDate = birthDate;
    }


    public ArrayList<Production> getIsIn(){
        return productionsIsIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    @Override
    public boolean save() {
        return false;
    }

    @Override
    public Object load() {
        return null;
    }
}
