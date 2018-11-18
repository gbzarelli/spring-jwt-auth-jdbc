package br.com.helpdev.springjwtauthjdbc.model;

/**
 * Classe para mapeamento de entrada do login
 * <p>
 * Class mapping input login
 */
public class AuthRequestModel {

    private String username;
    private String password;

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

    @Override
    public String toString() {
        return "AuthRequestModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
