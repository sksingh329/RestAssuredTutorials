package app.e2e.crocodiles.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrocodilesPOJOUsingLombok {
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

    public CrocodilesPOJOUsingLombok(String crocodileName,String gender,String crocodileDOB){
        this.crocodileName = crocodileName;
        this.gender = gender;
        this.crocodileDOB = crocodileDOB;
    }
}
