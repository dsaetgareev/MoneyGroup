package moneygroup.devufa.ru.moneygroup.model.enums;

public enum Status {
    //Новый
    NEW,
    // в архив
    IN_ARCHIVE,
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
