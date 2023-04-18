package com.mobileapp.a4cast;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.mobileapp.a4cast.DatabaseItem;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SQLiteManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "weather4CastMain.db";
    private static final int DB_VERSION = 1;
    public static String DB_PATH = "";

    public static final String TABLE_NAME = "weather";

    public static final String COL_1 = "TYPE";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "MIN_TEMP";
    public static final String COL_4 = "MAX_TEMP";
    public static final String COL_5 = "CONDITIONS";
    public static final String COL_6 = "LINK";
    /*
    public static final int TYPE = 0;
    public static final int NAME = 1;
    public static final int MIN_TEMP = 2;
    public static final int MAX_TEMP = 3;
    public static final int CONDITIONS = 4;
    public static final int LINK = 5;
     */

    public Context context;
    static SQLiteDatabase sqliteDataBase;

    public SQLiteManager(Context context) {
        super(context, DB_NAME, null ,DB_VERSION);
        this.context = context;
        DB_PATH = context.getDatabasePath(DB_NAME).getParent() + "/";
        Log.d("DEBUG", "DB_PATH: " + DB_PATH);
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * By calling this method and empty database will be created into the default system path
     * of your application so we are gonna be able to overwrite that database with our database.
     * */
    public void createDataBase() throws IOException {
        /*
        Log.d("DEBUG", "DB: onCreateDatabase");
        //check if the database exists
        boolean databaseExist = checkDataBase();
        if(databaseExist){
            Log.d("DEBUG", "DB: NO COPY");
            // Do Nothing.
        }else{
            Log.d("DEBUG", "DB: COPY");
            this.getWritableDatabase();
            copyDataBase();
        }// end if else dbExist
         */
        Log.d("DEBUG", "DB: onCreateDatabase");

        this.getWritableDatabase(); // This will create an empty database if it doesn't exist

        // Always copy the new database, even if the old one exists
        try {
            copyDataBase();
        } catch (IOException e) {
            throw new Error("Error copying new database");
        }
    } // end createDataBase().

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DB_NAME);
        return databaseFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring byte stream.
     * */
    private void copyDataBase() throws IOException{
        Log.d("DEBUG", "DB: copyDataBase");
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * This method opens the data base connection.
     * First it create the path up till data base of the device.
     * Then create connection with data base.
     */
    public void openDataBase() throws SQLException{
        //Open the database
        sqliteDataBase = SQLiteDatabase.openDatabase(DB_PATH+DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This Method is used to close the data base connection.
     */
    @Override
    public synchronized void close() {
        if(sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No need to write the create table query.
        // As we are using Pre built data base.
        // Which is ReadOnly.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No need to write the update table query.
        // As we are using Pre built data base.
        // Which is ReadOnly.
        // We should not update it as requirements of application.
    }

    //GET METHODS
    //SNOW, RAIN, DRIZZLE, THUNDERSTORM, CLEAR, CLOUDS

    // Method to get items by TEMP (input one int and gets all items that have that in range)
    public List<DatabaseItem> getItemsByTemp(int temperature) {
        //Log.d("DEBUG", "DB: TEMP: " + temperature);
        SQLiteDatabase db = this.getReadableDatabase();
        List<DatabaseItem> itemList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE MIN_TEMP <= ? AND MAX_TEMP >= ?";
        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(temperature), String.valueOf(temperature) });
        if (cursor.moveToFirst()) {
            do {
                //String recommendation = cursor.getString(0);
                //recommendations.add(recommendation);
                DatabaseItem item = new DatabaseItem();
                //item.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                //Log.d("DEBUG", "DB: ITEM NAME: " + cursor.getString(1));
                item.setType(cursor.getString(0));
                item.setName(cursor.getString(1));
                item.setMinTemp(cursor.getInt(2));
                item.setMaxTemp(cursor.getInt(3));
                item.setConditions(cursor.getString(4));
                item.setLink(cursor.getString(5));
                item.setRecipe(cursor.getString(6));
                item.setComment(cursor.getString(7));
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    // Method to get items by TYPE
    public List<DatabaseItem> getItemsByType(String type) {
        List<DatabaseItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE TYPE = ?";
        Cursor cursor = db.rawQuery(query, new String[]{type});
        if (cursor.moveToFirst()) {
            do {
                DatabaseItem item = new DatabaseItem();
                //item.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                item.setType(cursor.getString(0));
                item.setName(cursor.getString(1));
                item.setMinTemp(cursor.getInt(2));
                item.setMaxTemp(cursor.getInt(3));
                item.setConditions(cursor.getString(4));
                item.setLink(cursor.getString(5));
                item.setRecipe(cursor.getString(6));
                item.setComment(cursor.getString(7));
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    // Method to get items by CONDITIONS //SNOW, RAIN, DRIZZLE, THUNDERSTORM, CLEAR, CLOUDS
    public List<DatabaseItem> getItemsByConditions(String conditions) {
        List<DatabaseItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE CONDITIONS LIKE ? OR CONDITIONS LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + conditions + "%", "%ANY%"});
        if (cursor.moveToFirst()) {
            do {
                DatabaseItem item = new DatabaseItem();
                item.setType(cursor.getString(0));
                item.setName(cursor.getString(1));
                item.setMinTemp(cursor.getInt(2));
                item.setMaxTemp(cursor.getInt(3));
                item.setConditions(cursor.getString(4));
                item.setLink(cursor.getString(5));
                item.setRecipe(cursor.getString(6));
                item.setComment(cursor.getString(7));
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }


    // Method to get items by CONDITIONS and TYPE
    public List<DatabaseItem> getItemsByTypeAndConditions(String type, String conditions) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DatabaseItem> itemList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE CONDITIONS = ? AND TYPE = ?";
        Cursor cursor = db.rawQuery(query, new String[]{type, conditions});
        if (cursor.moveToFirst()) {
            do {
                DatabaseItem item = new DatabaseItem();
                //item.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                item.setType(cursor.getString(0));
                item.setName(cursor.getString(1));
                item.setMinTemp(cursor.getInt(2));
                item.setMaxTemp(cursor.getInt(3));
                item.setConditions(cursor.getString(4));
                item.setLink(cursor.getString(5));
                item.setRecipe(cursor.getString(6));
                item.setComment(cursor.getString(7));
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }
}

