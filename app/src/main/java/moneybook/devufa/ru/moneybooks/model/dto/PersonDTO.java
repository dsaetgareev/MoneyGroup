package moneybook.devufa.ru.moneybooks.model.dto;

import java.io.Serializable;

public class PersonDTO implements Serializable {
    private String number;
    private String photo;
    private String email;
    private String question;
    private String password;

    public PersonDTO() {
    }

    public PersonDTO(String number, String photo, String email, String question) {
        this.number = number;
        this.photo = photo;
        this.email = email;
        this.question = question;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
