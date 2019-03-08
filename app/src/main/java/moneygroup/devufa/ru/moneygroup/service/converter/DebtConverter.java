package moneygroup.devufa.ru.moneygroup.service.converter;

import android.content.Context;

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

}
