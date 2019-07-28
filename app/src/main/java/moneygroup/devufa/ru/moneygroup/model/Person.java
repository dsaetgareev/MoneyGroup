package moneygroup.devufa.ru.moneygroup.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import moneygroup.devufa.ru.moneygroup.model.enums.Status;

public class Person implements Serializable {

    private UUID id;

    private String createDate;

    private String modifiedDate;

    private String creator;

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

    private String initiator;

    private String nameForInitiator;

    private String receiver;

    private String nameForReceiver;

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getNameForInitiator() {
        return nameForInitiator;
    }

    public void setNameForInitiator(String nameForInitiator) {
        this.nameForInitiator = nameForInitiator;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNameForReceiver() {
        return nameForReceiver;
    }

    public void setNameForReceiver(String nameForReceiver) {
        this.nameForReceiver = nameForReceiver;
    }
}
