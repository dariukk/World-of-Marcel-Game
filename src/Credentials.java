// DONE
//  Credentials este o clasa care contine:
//  -   datele de autentificare ale utilizatorului (email si parola)
//  Aceasta clasa va fi implementata respectand principiul incapsularii.

public class Credentials {
    private String email;
    private String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
