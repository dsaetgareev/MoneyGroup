package moneygroup.devufa.ru.moneygroup.model.dto;

import java.util.UUID;

public class CountryCode {

    private String code;

    private String name;

    public CountryCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public CountryCode(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
