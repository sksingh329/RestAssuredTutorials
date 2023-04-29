package app.e2e.crocodiles.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrocodilesPOJO {
    @JsonProperty("name")
    private String crocodileName;
    @JsonProperty("sex")
    private String gender;
    @JsonProperty("date_of_birth")
    private String crocodileDOB;
    @JsonProperty("age")
    private int age;
    @JsonProperty("id")
    private int id;

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CrocodilesPOJO(){

    }

    public CrocodilesPOJO(String crocodileName, String gender, String crocodileDOB) {
        this.crocodileName = crocodileName;
        this.gender = gender;
        this.crocodileDOB = crocodileDOB;
    }

    public String getCrocodileName() {
        return crocodileName;
    }

    public void setCrocodileName(String crocodileName) {
        this.crocodileName = crocodileName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCrocodileDOB() {
        return crocodileDOB;
    }

    public void setCrocodileDOB(String crocodileDOB) {
        this.crocodileDOB = crocodileDOB;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }
}
