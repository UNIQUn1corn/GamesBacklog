package com.example.marmm.gamesbacklog.data;

import android.provider.BaseColumns;

/**
 * Created by marmm on 11/02/2018.
 */

public final class GamesContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private GamesContract() {}

    /* Inner class that defines the table contents */
    public static class GameEntry implements BaseColumns {
        // Labels Table Columns names
        public static final String TABLE_NAME = "Games";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PLATFORM = "platform";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_NOTES = "notes";
    }
}

