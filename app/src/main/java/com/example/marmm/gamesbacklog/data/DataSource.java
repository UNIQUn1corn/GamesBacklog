package com.example.marmm.gamesbacklog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marmm on 11/02/2018.
 */


public class DataSource {

       private SQLiteDatabase mDatabase;
       private final DBHelper mDBHelper;
       private final String[] GAMES_ALL_COLUMNS = {
                GamesContract.GameEntry.COLUMN_NAME_ID,
                GamesContract.GameEntry.COLUMN_NAME_TITLE,
                GamesContract.GameEntry.COLUMN_NAME_PLATFORM,
                GamesContract.GameEntry.COLUMN_NAME_DATE,
                GamesContract.GameEntry.COLUMN_NAME_STATUS,
                GamesContract.GameEntry.COLUMN_NAME_NOTES };


    // Opens the mDatabase to use it
    public void open()  {
        mDatabase = mDBHelper.getWritableDatabase();
    }
    // Closes the mDatabase when you no longer need it
    public void close() {
        mDBHelper.close();
    }


    public DataSource(Context context) {
            mDBHelper = new DBHelper(context);
        }

        /**
         * Save an object within the mDatabase.
         *
         * @param game the object to be saved.
         */
        public void save(Game game) {
            ContentValues values = new ContentValues();
            values.put(GamesContract.GameEntry.COLUMN_NAME_TITLE, game.getTitle());
            values.put(GamesContract.GameEntry.COLUMN_NAME_PLATFORM, game.getPlatform());
            values.put(GamesContract.GameEntry.COLUMN_NAME_DATE, game.getDateAdded());
            values.put(GamesContract.GameEntry.COLUMN_NAME_STATUS, game.getStatus());
            values.put(GamesContract.GameEntry.COLUMN_NAME_NOTES, game.getNotes());
            // Inserting Row
            mDatabase.insert(GamesContract.GameEntry.TABLE_NAME, null, values);
            mDatabase.close();
        }

        /**
         * Update a single entity within the mDatabase.
         *
         * @param id   the id of the entity to be updated.
         * @param game holds the new values which will overwrite the old values.
         */
        public void update(int id, Game game) {

            ContentValues values = new ContentValues();
            values.put(GamesContract.GameEntry.COLUMN_NAME_TITLE, game.getTitle());
            values.put(GamesContract.GameEntry.COLUMN_NAME_PLATFORM, game.getPlatform());
            values.put(GamesContract.GameEntry.COLUMN_NAME_DATE, game.getDateAdded());
            values.put(GamesContract.GameEntry.COLUMN_NAME_STATUS, game.getStatus());
            values.put(GamesContract.GameEntry.COLUMN_NAME_NOTES, game.getNotes());

            mDatabase.update(GamesContract.GameEntry.TABLE_NAME, values, GamesContract.GameEntry.COLUMN_NAME_ID + "= ?", new String[]{String.valueOf(id)});
            mDatabase.close(); // Closing mDatabase connection
        }


        /**
         * Finds all game objects.
         *
         * @return a cursor holding the game objects.
         */
        public Cursor findAll() {
            return mDatabase.query(GamesContract.GameEntry.TABLE_NAME, GAMES_ALL_COLUMNS, null, null, null, null, null);
        }

        /**
         * Delete a single entity from the mDatabase.
         *
         * @param id the id of the entity to be deleted.
         */
        public void delete(int id) {

            mDatabase.delete(GamesContract.GameEntry.TABLE_NAME, GamesContract.GameEntry.COLUMN_NAME_ID + " =?",
                    new String[]{Integer.toString(id)});

        }
    }

