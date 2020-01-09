package moneybook.devufa.ru.moneybooks.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class CycleDTO implements Serializable {

    private UUID id;

    private String status;

    private int countElement;

    private double minCount;

    private int acceptedPerson;

    private String cycleCurrency;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCountElement() {
        return countElement;
    }

    public void setCountElement(int countElement) {
        this.countElement = countElement;
    }

    public double getMinCount() {
        return minCount;
    }

    public void setMinCount(double minCount) {
        this.minCount = minCount;
    }

    public int getAcceptedPerson() {
        return acceptedPerson;
    }

    public void setAcceptedPerson(int acceptedPerson) {
        this.acceptedPerson = acceptedPerson;
    }

    public String getCycleCurrency() {
        return cycleCurrency;
    }

    public void setCycleCurrency(String cycleCurrency) {
        this.cycleCurrency = cycleCurrency;
    }
}
