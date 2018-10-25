package com.josericardojunior.persistenciadadosfim;

import android.content.ContentValues;
import android.content.Context;
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
}
