package com.example.assignment_0696;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SnackDatabaseHelper {

    static final String DATABASE_NAME = "CineFastDB";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_SNACKS = "snacks";
    static final String COLUMN_ID    = "id";
    static final String COLUMN_NAME  = "name";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_IMAGE = "image";

    Context context;
    DBHelper helper;

    public SnackDatabaseHelper(Context context) {
        this.context = context;
    }

    public void open() {
        helper = new DBHelper(context);
    }

    public void close() {
        helper.close();
    }

    public long insertSnack(String name, String price, String image) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,  name);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_IMAGE, image);
        long id = db.insert(TABLE_SNACKS, null, cv);
        db.close();
        return id;
    }
    public boolean isEmpty() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SNACKS, null, null, null, null, null, null);
        boolean empty = !cursor.moveToFirst();
        cursor.close();
        db.close();
        return empty;
    }

    public ArrayList<Snack> getAllSnacks() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SNACKS, null, null, null, null, null, null);

        ArrayList<Snack> snackList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int index_name  = cursor.getColumnIndex(COLUMN_NAME);
                int index_price = cursor.getColumnIndex(COLUMN_PRICE);
                int index_image = cursor.getColumnIndex(COLUMN_IMAGE);

                String name      = cursor.getString(index_name);
                String price     = cursor.getString(index_price);
                String imageName = cursor.getString(index_image);

                int imageRes = context.getResources().getIdentifier(
                        imageName, "drawable", context.getPackageName()
                );

                snackList.add(new Snack(imageRes, name, price));

            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return snackList;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + TABLE_SNACKS + "("
                    + COLUMN_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME  + " TEXT, "
                    + COLUMN_PRICE + " TEXT, "
                    + COLUMN_IMAGE + " TEXT)";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SNACKS);
            onCreate(db);
        }
    }
}