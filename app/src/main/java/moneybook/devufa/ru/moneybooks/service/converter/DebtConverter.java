package moneybook.devufa.ru.moneybooks.service.converter;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import moneybook.devufa.ru.moneybooks.model.Person;
import moneybook.devufa.ru.moneybooks.model.dto.DebtDTO;
import moneybook.devufa.ru.moneybooks.service.CodeService;

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
        if (person.isOwesMe()) {
            debtDTO.setInitiator(number);
            debtDTO.setReceiver(person.getNumber());
            debtDTO.setNameForReceiver(person.getName());
        } else {
            debtDTO.setInitiator(person.getNumber());
            debtDTO.setReceiver(number);
            debtDTO.setNameForInitiator(person.getName());
        }
        debtDTO.setCount(Double.valueOf(person.getSumm()));
        debtDTO.setCurrency(person.getCurrency());
        debtDTO.setComment(person.getComment());
        debtDTO.setNote(person.getNote());
        return debtDTO;
    }

    public Person convertToPerson(DebtDTO debtDTO) {
        if (number == null) {
            init();
        }
        Person person = new Person();
        person.setId(UUID.fromString(debtDTO.getId()));
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        person.setCreateDate(format.format(debtDTO.getCreateDate()));
        person.setModifiedDate(format.format(debtDTO.getModifiedDate()));
        person.setCreator(debtDTO.getCreator());
        if (debtDTO.getInitiator().equals(number)) {
            person.setNumber(debtDTO.getReceiver());
            person.setName(debtDTO.getNameForReceiver() != null ? debtDTO.getNameForReceiver() : debtDTO.getReceiver());
        } else {
            person.setNumber(debtDTO.getInitiator());
            person.setName(debtDTO.getNameForInitiator() != null ? debtDTO.getNameForInitiator() : debtDTO.getInitiator());
        }
        person.setSumm(String.valueOf(debtDTO.getCount()));
        person.setCurrency(debtDTO.getCurrency());
        if (debtDTO.getReceiver().equals(number)) {
            person.setOwesMe(false);
        }
        person.setComment(debtDTO.getComment());
        person.setNote(debtDTO.getNote());
        person.setStatus(debtDTO.getStatus());
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
