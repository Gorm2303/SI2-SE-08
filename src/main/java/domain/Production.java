package domain;

import java.util.Date;

public class Production {
    private String name;
    private int id;
    private Organization producer;
    private Date releaseDate;
    private String programCategory;
    private int length;


    public Production() {
    }

    public Production(String name, int id, Organization producer, Date releaseDate, String programCategory, int length) {
        this.name = name;
        this.id = id;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.programCategory = programCategory;
        this.length = length;
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

    public Organization getProducer() {
        return producer;
    }

    public void setProducer(Organization producer) {
        this.producer = producer;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getProgramCategory() {
        return programCategory;
    }

    public void setProgramCategory(String programCategory) {
        this.programCategory = programCategory;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
