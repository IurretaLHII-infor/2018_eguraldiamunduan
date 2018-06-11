package com.example.sasiroot.eguraldia_munduan;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by taquera on 20/04/2018.
 */

public class eguraldiaDbOpenHelper extends SQLiteOpenHelper{

    public eguraldiaDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE Eguraldia (hiria TEXT,uneko_tenperatura TEXT, komentarioa TEXT, predikzioa TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
