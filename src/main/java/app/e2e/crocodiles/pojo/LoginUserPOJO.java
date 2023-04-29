package app.e2e.crocodiles.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginUserPOJO {
    @JsonProperty("username")
    private String userName;
    @JsonProperty("password")
    private String password;

    public LoginUserPOJO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
