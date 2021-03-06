package moneybook.devufa.ru.moneybooks.dao.person;

public class PersonDao {

    public static final class PersonTable {

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
            public static final String COUNTRY_CODE = "country_code";
        }
    }

    public static final class ArchiveTable {
        public static final String NAME = "archive";

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

    public static final class CodeTable {
        public static final String NAME = "code";

        public static final class Cols {
            public static final String NUMBER = "number";
            public static final String CODE = "code";
        }
    }

    public static final class LocaleTable {
        public static final String NAME = "locale";

        public static final class Cols {
            public static final String LOCALE_NAME = "locale_name";
            public static final String LOCALE = "locale";
        }
    }
}
