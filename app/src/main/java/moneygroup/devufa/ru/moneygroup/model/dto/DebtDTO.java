package moneygroup.devufa.ru.moneygroup.model.dto;

import java.io.Serializable;

import moneygroup.devufa.ru.moneygroup.model.enums.DebtType;
import moneygroup.devufa.ru.moneygroup.model.enums.Status;

public class DebtDTO implements Serializable {

    private String initiator;

    private String receiver;

    private double count;

    private String currency;

    private String comment;

    private String note;

    private DebtType debtType;

    private Status status;

    private double minCountInCycle;

    private String prevInCycle;

    private String nextInCycle;

    public DebtDTO() {
    }

    public DebtDTO(String initiator, String receiver, double count, String currency, String comment, String note, DebtType debtType, Status status, double minCountInCycle, String prevInCycle, String nextInCycle) {
        this.initiator = initiator;
        this.receiver = receiver;
        this.count = count;
        this.currency = currency;
        this.comment = comment;
        this.note = note;
        this.debtType = debtType;
        this.status = status;
        this.minCountInCycle = minCountInCycle;
        this.prevInCycle = prevInCycle;
        this.nextInCycle = nextInCycle;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getMinCountInCycle() {
        return minCountInCycle;
    }

    public void setMinCountInCycle(double minCountInCycle) {
        this.minCountInCycle = minCountInCycle;
    }

    public String getPrevInCycle() {
        return prevInCycle;
    }

    public void setPrevInCycle(String prevInCycle) {
        this.prevInCycle = prevInCycle;
    }

    public String getNextInCycle() {
        return nextInCycle;
    }

    public void setNextInCycle(String nextInCycle) {
        this.nextInCycle = nextInCycle;
    }
}
