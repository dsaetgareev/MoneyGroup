package moneygroup.devufa.ru.moneygroup.model.enums;

public enum Status {
    //Новый
    NEW,
    //Ожидат регистрации
    NOT_REGISTERED,
    //Ожидает подтверждения
    WAITING_FOR_ACCEPTING,
    //Ожидает оплаты
    WAITING_FOR_PAYMENT,
    //Отказано
    DECLINED,
    //Подтвержден
    ACCEPTED,
    //Закрыт
    CLOSED
}
