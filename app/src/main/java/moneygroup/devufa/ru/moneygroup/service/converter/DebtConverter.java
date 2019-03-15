package moneygroup.devufa.ru.moneygroup.service.converter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.dto.DebtDTO;
import moneygroup.devufa.ru.moneygroup.model.enums.DebtType;
import moneygroup.devufa.ru.moneygroup.service.CodeService;

public class DebtConverter {

    private Context context;

    private CodeService codeService;

    private String number;

    public DebtConverter(Context context) {
        this.context = context;
    }

    private void init() {
        codeService = CodeService.get(context);
        number = codeService.getNumber();
    }

    public DebtDTO convertToDebtDTO(Person person) {
        if (number == null) {
            init();
        }
        DebtDTO debtDTO = new DebtDTO();

        debtDTO.setInitiator(number);
        debtDTO.setReceiver(person.getNumber());
        debtDTO.setCount(Double.valueOf(person.getSumm()));
        debtDTO.setCurrency(person.getCurrency());
        debtDTO.setComment(person.getComment());
        debtDTO.setNote(person.getNote());
        debtDTO.setDebtType(person.isOwesMe() ? DebtType.LOAN : DebtType.DEBT);

        return debtDTO;
    }

    public Person convertToPerson(DebtDTO debtDTO) {
        Person person = new Person();
        person.setNumber(debtDTO.getReceiver());
        person.setName(debtDTO.getReceiver());
        person.setSumm(String.valueOf(debtDTO.getCount()));
        person.setCurrency(debtDTO.getCurrency());
        if (debtDTO.getDebtType().equals(DebtType.DEBT)) {
            person.setOwesMe(false);
        }
        person.setComment(debtDTO.getComment());
        person.setNote(debtDTO.getNote());
        return person;
    }

    public List<Person> convertToPersonList(List<DebtDTO> debtDTOS) {
        List<Person> personList = new ArrayList<>();
        for (DebtDTO debtDTO : debtDTOS) {
            Person person = convertToPerson(debtDTO);
            personList.add(person);
        }
        return personList;
    }

}
