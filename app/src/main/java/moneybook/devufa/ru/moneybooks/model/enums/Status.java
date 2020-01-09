package moneybook.devufa.ru.moneybooks.model.enums;

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
    CLOSED,
    //Закрыт
    CLOSED_INITIATOR,
    //Закрыт
    CLOSED_RECEIVER;
}
