package moneygroup.devufa.ru.moneygroup.model.dto;

import java.io.Serializable;

import moneygroup.devufa.ru.moneygroup.model.enums.DebtType;

public class DebtDTO implements Serializable {

    private String initiator;

    private String receiver;

    private double count;

    private String currency;

    private String comment;

    private String note;

    private DebtType debtType;

    public DebtDTO() {
    }

    public DebtDTO(String initiator, String receiver, double count, String currency, String comment, String note, DebtType debtType) {
        this.initiator = initiator;
        this.receiver = receiver;
        this.count = count;
        this.currency = currency;
        this.comment = comment;
        this.note = note;
        this.debtType = debtType;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public DebtType getDebtType() {
        return debtType;
    }

    public void setDebtType(DebtType debtType) {
        this.debtType = debtType;
    }
}
