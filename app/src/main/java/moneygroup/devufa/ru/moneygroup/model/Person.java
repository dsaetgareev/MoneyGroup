package moneygroup.devufa.ru.moneygroup.model;

import java.util.UUID;

public class Person {

    private UUID id;

    private String name;

    private String countryCode;

    private String number;

    private String password;

    private String summ;

    private boolean isOwesMe;

    private String currency;

    private String note;

    private String comment;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (isOwesMe != person.isOwesMe) return false;
        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (countryCode != null ? !countryCode.equals(person.countryCode) : person.countryCode != null)
            return false;
        if (number != null ? !number.equals(person.number) : person.number != null) return false;
        if (password != null ? !password.equals(person.password) : person.password != null)
            return false;
        if (summ != null ? !summ.equals(person.summ) : person.summ != null) return false;
        if (currency != null ? !currency.equals(person.currency) : person.currency != null)
            return false;
        if (note != null ? !note.equals(person.note) : person.note != null) return false;
        return comment != null ? comment.equals(person.comment) : person.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (summ != null ? summ.hashCode() : 0);
        result = 31 * result + (isOwesMe ? 1 : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
