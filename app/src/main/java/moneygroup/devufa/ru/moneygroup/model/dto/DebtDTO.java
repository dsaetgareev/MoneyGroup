package moneygroup.devufa.ru.moneygroup.model.dto;

import java.io.Serializable;
import java.util.Date;

import moneygroup.devufa.ru.moneygroup.model.enums.DebtType;
import moneygroup.devufa.ru.moneygroup.model.enums.Status;

public class DebtDTO implements Serializable {

    private String id;

    private Date createDate;

    private String initiator;

    private String nameForInitiator;

    private String receiver;

    private String nameForReceiver;

    private double totalCount;

    private double count;

    private String currency;

    private String comment;

    private String note;

    private DebtType debtType;

    private Status status;

    private boolean relief;

    public DebtDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public double getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(double totalCount) {
        this.totalCount = totalCount;
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

    public boolean isRelief() {
        return relief;
    }

    public void setRelief(boolean relief) {
        this.relief = relief;
    }

    public String getNameForInitiator() {
        return nameForInitiator;
    }

    public void setNameForInitiator(String nameForInitiator) {
        this.nameForInitiator = nameForInitiator;
    }

    public String getNameForReceiver() {
        return nameForReceiver;
    }

    public void setNameForReceiver(String nameForReceiver) {
        this.nameForReceiver = nameForReceiver;
    }
}
