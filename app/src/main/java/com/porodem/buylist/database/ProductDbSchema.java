package com.porodem.buylist.database;

public class ProductDbSchema {
    public static final class ProductTable {
        public static final String NAME = "products";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String TYPE = "p_type";
        }
    }

    public static final class ChequeTable {
        public static final String NAME = "cheques";

        public static final class Cols {
            public static final String DATE = "date"; // day and time of day
            public static final String MONTH = "month";
            public static final String YEAR = "year";
            public static final String LABEL = "label";
            public static final String SUM = "sum";
        }
        public static final class Zols {
            public static final String TIME = "time";
            public static final String DAY = "date";
            public static final String MONTH = "month";
            public static final String YEAR = "year";
            public static final String LABEL = "label";
            public static final String SUM = "sum";
        }
    }
}


