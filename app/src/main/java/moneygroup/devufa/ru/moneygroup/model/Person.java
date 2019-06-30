package moneygroup.devufa.ru.moneygroup.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import moneygroup.devufa.ru.moneygroup.model.enums.Status;

public class Person implements Serializable {

    private UUID id;

    private Date createDate;

    private String name;

    private String number;

    private String password;

    private String summ;

    private boolean isOwesMe = true;

    private String currency;

    private String note;

    private String comment;

    private Status status;

    private String countryCody;

    public Person() {
        id = UUID.randomUUID();
    }

    public Person(UUID id) {
        this.id = id;

    }

    public Person(String name, String summ) {
        id = UUID.randomUUID();
        this.name = name;
        this.summ = summ;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSumm() {
        return summ;
    }

    public void setSumm(String summ) {
        this.summ = summ;
    }

    public boolean isOwesMe() {
        return isOwesMe;
    }

    public void setOwesMe(boolean owesMe) {
        isOwesMe = owesMe;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCountryCody() {
        return countryCody;
    }

    public void setCountryCody(String countryCody) {
        this.countryCody = countryCody;
    }
}
