package com.example.marmm.gamesbacklog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marmm on 11/02/2018.
 */


    public class GameRepository {

        private SQLiteDatabase database;
        private final DBHelper dbHelper;
        private final String[] GAMES_ALL_COLUMNS = {
                GamesContract.GameEntry.COLUMN_NAME_ID,
                GamesContract.GameEntry.COLUMN_NAME_TITLE,
                GamesContract.GameEntry.COLUMN_NAME_PLATFORM,
                GamesContract.GameEntry.COLUMN_NAME_DATE,
                GamesContract.GameEntry.COLUMN_NAME_STATUS,
                GamesContract.GameEntry.COLUMN_NAME_NOTES };

        public GameRepository(Context context) {
            dbHelper = new DBHelper(context);
        }

        /**
         * Save an object within the database.
         *
         * @param game the object to be saved.
         */
        public void save(Game game) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(GamesContract.GameEntry.COLUMN_NAME_TITLE, game.getTitle());
            values.put(GamesContract.GameEntry.COLUMN_NAME_PLATFORM, game.getPlatform());
            values.put(GamesContract.GameEntry.COLUMN_NAME_DATE, game.getDateAdded());
            values.put(GamesContract.GameEntry.COLUMN_NAME_STATUS, game.getStatus());
            values.put(GamesContract.GameEntry.COLUMN_NAME_NOTES, game.getNotes());
            // Inserting Row
            db.insert(GamesContract.GameEntry.TABLE_NAME, null, values);
            db.close();
        }

        /**
         * Update a single entity within the database.
         *
         * @param id   the id of the entity to be updated.
         * @param game holds the new values which will overwrite the old values.
         */
        public void update(int id, Game game) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(GamesContract.GameEntry.COLUMN_NAME_TITLE, game.getTitle());
            values.put(GamesContract.GameEntry.COLUMN_NAME_PLATFORM, game.getPlatform());
            values.put(GamesContract.GameEntry.COLUMN_NAME_DATE, game.getDateAdded());
            values.put(GamesContract.GameEntry.COLUMN_NAME_STATUS, game.getStatus());
            values.put(GamesContract.GameEntry.COLUMN_NAME_NOTES, game.getNotes());

            db.update(GamesContract.GameEntry.TABLE_NAME, values, GamesContract.GameEntry.COLUMN_NAME_ID + "= ?", new String[]{String.valueOf(id)});
            db.close(); // Closing database connection
        }


        /**
         * Finds all game objects.
         *
         * @return a cursor holding the game objects.
         */
        public Cursor findAll() {
            // If we have not yet opened the database, open it
            if (database == null) {
                database = dbHelper.getReadableDatabase();
            }

            return database.query(GamesContract.GameEntry.TABLE_NAME, GAMES_ALL_COLUMNS, null, null, null, null, null);
        }

        /**
         * Delete a single entity from the database.
         *
         * @param id the id of the entity to be deleted.
         */
        public void delete(int id) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.delete(GamesContract.GameEntry.TABLE_NAME, GamesContract.GameEntry.COLUMN_NAME_ID + " =?",
                    new String[]{Integer.toString(id)});
            db.close();
        }
    }

