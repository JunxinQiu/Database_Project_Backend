package fudan.se.lab2.controller.request;

import java.util.Set;

/**
 * @author LBW
 */
public class RegisterRequest {
    private String username;
    private String password;
    private String fullname;
    private String authorities;
    private String email;
    public RegisterRequest() {}

    public RegisterRequest(String username, String password, String fullname, String authorities,String email) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.authorities = authorities;
        this.email=email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

}

