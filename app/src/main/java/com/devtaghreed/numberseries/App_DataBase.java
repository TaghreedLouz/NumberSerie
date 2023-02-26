package com.devtaghreed.numberseries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class App_DataBase extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "DB_GAME_player";
    public static final String GAME_TB_NAME = "game";
    public static final String GAME_CLN_USERID = "user_id";
    public static final String GAME_CLN_SCORE = "score";
    public static final String GAME_CLN_DATE = "date";

    String date, Username;
    int score;

    public App_DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + GAME_TB_NAME + "( " + GAME_CLN_USERID + " TEXT, "
                + GAME_CLN_SCORE + " INTEGER, " + GAME_CLN_DATE + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GAME_TB_NAME);
        onCreate(sqLiteDatabase);
    }

    //insert
    public boolean insert_game(Game game) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GAME_CLN_USERID, game.getUsername());
        values.put(GAME_CLN_SCORE, game.getScore());
        values.put(GAME_CLN_DATE, game.getDate());
        long result = db.insert(GAME_TB_NAME, null, values);
        return result != -1;
    }

    //destroy
    public void destroy_game() {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(GAME_TB_NAME, null, null);
    }

    //select
    public ArrayList<Game> select_all_game() {
        ArrayList<Game> games = new ArrayList<>();
        SQLiteDatabase dp = getReadableDatabase();
        Cursor cursor = dp.rawQuery("SELECT * FROM " + GAME_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Username = cursor.getString(cursor.getColumnIndexOrThrow(GAME_CLN_USERID));
                date = cursor.getString(cursor.getColumnIndexOrThrow(GAME_CLN_DATE));
                score = cursor.getInt(cursor.getColumnIndexOrThrow(GAME_CLN_SCORE));
                Game game = new Game(Username, date, score);
                games.add(game);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return games;
    }

    //date
    public String select_date() {
        String last_date;
        SQLiteDatabase dp2 = getReadableDatabase();
        Cursor cursor = dp2.rawQuery("SELECT " + GAME_CLN_DATE + " FROM " + GAME_TB_NAME, null);
        if (cursor.moveToLast()) {
            last_date = cursor.getString(cursor.getColumnIndexOrThrow(GAME_CLN_DATE));
        } else {
            last_date = " ";
        }
        cursor.close();
        return last_date;
    }

    //score
    public int select_score() {
        int last_score;
        SQLiteDatabase dp2 = getReadableDatabase();
        Cursor cursor = dp2.rawQuery("SELECT " + GAME_CLN_SCORE + " FROM " + GAME_TB_NAME, null);
        if (cursor.moveToLast()) {
            last_score = cursor.getInt(cursor.getColumnIndexOrThrow(GAME_CLN_SCORE));
        } else {
            last_score = 0;
        }
        cursor.close();
        return last_score;
    }
}