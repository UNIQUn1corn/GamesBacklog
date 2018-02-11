package com.example.marmm.gamesbacklog.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marmm on 11/02/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 1;

    // Creating the table
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + GamesContract.GameEntry.TABLE_NAME +
                    "(" +
                    GamesContract.GameEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + GamesContract.GameEntry.COLUMN_NAME_TITLE + " TEXT, "
                    + GamesContract.GameEntry.COLUMN_NAME_PLATFORM + " TEXT, "
                    + GamesContract.GameEntry.COLUMN_NAME_DATE + " TEXT, "
                    + GamesContract.GameEntry.COLUMN_NAME_STATUS + " TEXT, "
                    + GamesContract.GameEntry.COLUMN_NAME_NOTES + " TEXT )";

    //Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GamesContract.GameEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}


