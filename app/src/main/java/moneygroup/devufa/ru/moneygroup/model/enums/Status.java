package moneygroup.devufa.ru.moneygroup.model.enums;

public enum Status {
    //Новый
    NEW,
    //Ожидат регистрации
    NOT_REGISTERED,
    //Отказано
    DECLINED,
    //Подтвержден
    ACCEPTED,
    IN_CYCLE_NEW,
    IN_CYCLE_ACCEPTED,
    //Закрыт
    CLOSED;
}
