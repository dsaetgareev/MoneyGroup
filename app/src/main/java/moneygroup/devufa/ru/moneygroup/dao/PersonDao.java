package moneygroup.devufa.ru.moneygroup.dao;

public class PersonDao {

    public static final class PersonTable{

        public static final String NAME = "persons";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String NUMBER = "number";
            public static final String SUMM = "summ";
            public static final String CURRENCY = "currency";
            public static final String NOTE = "note";
            public static final String COMMENT = "comment";
            public static final String IS_OWES_ME = "is_owes_me";
        }
    }
}
