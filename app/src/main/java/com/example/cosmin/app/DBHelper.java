package com.example.cosmin.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cosmin.app.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cosmin on 6/1/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    //Versiune db
    private static int DBVersion=1;
    //nume db,tabel.coloane,tag
    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "nume";
    public static final String USER_PASSWORD = "password";
    private static final String TAG ="tag" ;

    private static DBHelper sInstance;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DBVersion);
    }

    public static synchronized DBHelper getInstance(Context context) {
        //only one instance of db
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

            String CREATE_Student_table = "CREATE TABLE " + TABLE_NAME + "("
                    + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +USER_NAME + " TEXT , "
                    + USER_PASSWORD + " TEXT " + ")";

            db.execSQL(CREATE_Student_table);





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    /**
     * Metoda pentru a insera un user in db
     *
     * @param user
     */
    public boolean insertUser (User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_NAME, user.getName());
            contentValues.put(USER_PASSWORD, user.getPassword());
            db.insertOrThrow(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            Log.d(TAG,"Am adaugat user");
        }  catch (Exception e) {
            Log.d(TAG, "Error while trying to add country to database");
        } finally {
            db.endTransaction();

        }

        return true;
    }

    /**
     * Metoda pentru a face update unui user in db
     *
     * @param user
     */

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_NAME, user.getName());
            contentValues.put(USER_PASSWORD, user.getPassword());
        db.update(TABLE_NAME, contentValues, USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});

        }  catch (Exception e){
            Log.d(TAG, "Error while trying to add country to database");
        }  finally {
            db.endTransaction();

        }


        }

    /**
     * Metoda pentru a genera o lista cu toti userii din db , folosita pentru a creea RecycleView
     *
     * @return userList
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        User user = null;
        try {

            if (cursor.moveToFirst()) {
                do {
                    user = new User();
                    user.setId(Integer.parseInt(cursor.getString(0)));
                    user.setName(cursor.getString(1));
                    user.setPassword(cursor.getString(2));

                    // Adding Country to list
                    userList.add(user);
                    Log.d(TAG,"Am adaugat un student in lista");
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get countries from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return userList;
    }

    /**
     * Metoda pentru a sterge un user din db
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_NAME, USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }


}
