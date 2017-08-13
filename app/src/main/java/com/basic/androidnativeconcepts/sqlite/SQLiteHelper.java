package com.basic.androidnativeconcepts.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.basic.androidnativeconcepts.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAGARAJ SRJ on 8/13/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "books_db";

    // Books table name
    private static final String TABLE_CONTACTS = "books";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public boolean addBook (Book book) {
        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, book.getBookName()); // Contact Name
        values.put(KEY_ID, book.getBookId()); // Contact ID

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        flag = true;
        return flag;
    }

    public int getBooksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count  = cursor.getCount();
        db.close();
        cursor.close();
        // return count
        return count;
    }

    public Book getBookById(int id) {
        Book book = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        book = new Book(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        db.close();
        // return contact
        return book;
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setBookId(Integer.parseInt(cursor.getString(0)));
                book.setBookName((cursor.getString(1)));

                // Adding contact to list
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        db.close();
        return bookList;
    }

    public boolean updateBoook(Book book) {
        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, book.getBookId());
        values.put(KEY_NAME, book.getBookName());

        // updating row
        int rows = db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(book.getBookId()) });

        Log.i(SQLiteHelper.class.getCanonicalName(), "Rows count: "+ rows);
        if (rows > 0) {
            flag = true;
        }
        db.close();
        return flag;
    }

    public boolean deleteBoook(Book book) {
        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(book.getBookId()) });
        Log.i(SQLiteHelper.class.getCanonicalName(), "Rows count: "+ rows);
        if (rows > 0) {
            flag = true;
        }
        db.close();
        return flag;
    }
}
