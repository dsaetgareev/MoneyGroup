package moneybook.devufa.ru.moneybooks.model;

import java.io.Serializable;

public class BasicCode implements Serializable {

    private String number;
    private String code;

    public BasicCode(String number, String code) {
        this.number = number;
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
