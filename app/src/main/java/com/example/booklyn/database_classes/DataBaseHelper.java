package com.example.booklyn.database_classes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Rate;
import com.example.booklyn.entities.Room;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedReader;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseHelper extends SQLiteOpenHelper {
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.booklyn/databases/";
    // Data Base Name.
    private static final String DATABASE_NAME = "booklyn.db";
    // Data Base Version.
    private static final int DATABASE_VERSION = 1;
    // Table Names of Data Base.
    static final String TABLE_Name = "tableName";

    public Context context;
    static SQLiteDatabase sqliteDataBase;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void createDataBase() throws IOException {
        //check if the database exists
        boolean databaseExist = checkDataBase();

        if (databaseExist) {
            // Do Nothing.
        } else {
            this.getWritableDatabase();
            copyDataBase();
        }// end if else dbExist
    } // end createDataBase().

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase() {
        String path = DB_PATH + DATABASE_NAME;
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring byte stream.
     */
    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
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
    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This Method is used to close the data base connection.
     */
    @Override
    public synchronized void close() {
        if (sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }

    /**
     * Apply your methods and class to fetch data using raw or queries on data base using
     * following demo example code as:
     */

    private static final String TABLE_HOTEL_NAME = "Hotel";
    private static final String TABLE_HOTEL_KEY_ID = "_id";
    private static final String TABLE_HOTEL_KEY_NAME = "name";
    private static final String TABLE_HOTEL_MAIN_IMAGE_NAME = "mainPicture";
    private static final String TABLE_HOTEL_KEY_INFO = "info";

    private static final String TABLE_RATE_NAME = "Rate";
    private static final String TABLE_RATE_KEY_RATE = "rate";
    private static final String TABLE_RATE_KEY_INFO = "info";

    private static final String TABLE_ROOM_NAME = "Room";
    private static final String TABLE_ROOM_KEY_NAME = "name";
    private static final String TABLE_ROOM_KEY_INFO = "info";
    private static final String TABLE_ROOM_KEY_PRICE = "price";

    private static final String TABLE_IMAGES_NAME = "AdditionalImages";
    private static final String TABLE_IMAGES_KEY_PICTIRE = "picture";


    @SuppressLint("Range")
    public ArrayList<Hotel> getHotelsFromDatabase() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        Cursor cursor = sqliteDataBase.query(TABLE_HOTEL_NAME, null, null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            // Считваем рейтинг отеля
            Cursor cursorRating = sqliteDataBase.query(TABLE_RATE_NAME, null, "parent_id = ?",
                    new String[]{cursor.getString(cursor.getColumnIndex(TABLE_HOTEL_KEY_ID))}, null, null, null);
            ArrayList<Rate> rates = new ArrayList<>();
            while (cursorRating.moveToNext()) {
                rates.add(new Rate(cursorRating.getFloat(cursorRating.getColumnIndex(TABLE_RATE_KEY_RATE)),
                        cursorRating.getString(cursorRating.getColumnIndex(TABLE_RATE_KEY_INFO)), new Date()));
            }
            cursorRating.close();

            // Считваем рейтинг отеля
            Cursor cursorRooms = sqliteDataBase.query(TABLE_ROOM_NAME, null, "parent_id = ?",
                    new String[]{cursor.getString(cursor.getColumnIndex(TABLE_HOTEL_KEY_ID))}, null, null, null);
            ArrayList<Room> rooms = new ArrayList<>();
            while (cursorRooms.moveToNext()) {
                rooms.add(new Room(cursorRooms.getString(cursorRooms.getColumnIndex(TABLE_ROOM_KEY_NAME)),
                        cursorRooms.getString(cursorRooms.getColumnIndex(TABLE_ROOM_KEY_INFO)),
                        cursorRooms.getInt(cursorRooms.getColumnIndex(TABLE_ROOM_KEY_PRICE))));
            }
            cursorRooms.close();

            // Считваем доп.фотографии отеля
            Cursor cursorAdditionalImages = sqliteDataBase.query(TABLE_IMAGES_NAME, null, "parent_id = ?",
                    new String[]{cursor.getString(cursor.getColumnIndex(TABLE_HOTEL_KEY_ID))}, null, null, null);
            ArrayList<Integer> images = new ArrayList<>();
            while (cursorAdditionalImages.moveToNext()) {
                images.add(cursorAdditionalImages.getInt(cursorAdditionalImages.getColumnIndex(TABLE_IMAGES_KEY_PICTIRE)));
            }
            cursorAdditionalImages.close();
            //Создание отеля
            Hotel hotel = new Hotel(cursor.getInt(cursor.getColumnIndex(TABLE_HOTEL_KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(TABLE_HOTEL_KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(TABLE_HOTEL_KEY_INFO)),
                    cursor.getInt(cursor.getColumnIndex(TABLE_HOTEL_MAIN_IMAGE_NAME)),
                    rates, rooms, images);
            hotels.add(hotel);
        }
        cursor.close();
        return hotels;
    }

    public void writeFeedbackToDatabase(Rate rate, int hoteLID){
        ContentValues cv = new ContentValues();
        cv.put(TABLE_RATE_KEY_INFO, rate.getInfo());
        cv.put(TABLE_RATE_KEY_RATE, rate.getRate());
        cv.put("parent_id", hoteLID);
        sqliteDataBase.insert(TABLE_RATE_NAME, null, cv);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
