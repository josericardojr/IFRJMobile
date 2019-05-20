package com.josericardojunior.persistenciadadosfim;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "db_game";
    static final String TABLE_RANKING = "ranking";
    static int VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_RANKING +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  nickname STRING, " +
                "  score INTEGER);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RANKING);
        VERSION = newVersion;
        this.onCreate(db);
    }

    public void addGameScore(GameScore gs){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nickname", gs.nickname);
        values.put("score", gs.score);

        db.insert(TABLE_RANKING, null, values);
        db.close();

    }

    public GameScore retrieveGS(int id){
        GameScore gs = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RANKING,
                new String[]{"nickname", "score"},
                "id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            gs = new GameScore();
            gs.id = id;
            gs.nickname = cursor.getString(
                    cursor.getColumnIndex("nickname"));
            gs.score = cursor.getInt(
                    cursor.getColumnIndex("score"));
        }

        cursor.close();
        db.close();

        return gs;
    }
}
