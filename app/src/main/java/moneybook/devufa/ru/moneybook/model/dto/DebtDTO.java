package moneybook.devufa.ru.moneybook.model.dto;

import java.io.Serializable;
import java.util.Date;

import moneybook.devufa.ru.moneybook.model.enums.Status;

public class DebtDTO implements Serializable {

    private String id;

    private Date createDate;

    private Date modifiedDate;

    private String creator;

    private String initiator;

    private String nameForInitiator;

    private String receiver;

    private String nameForReceiver;

    private double totalCount;

    private double count;

    private String currency;

    private String comment;

    private String note;

    private Status status;

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

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
