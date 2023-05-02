package com.example.booklyn.database_classes;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Rate;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.User;

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

    public boolean checkDataBase() {
        String path = DB_PATH + DATABASE_NAME;
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

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

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }

    private static final String TABLE_HOTEL_NAME = "Hotel";
    private static final String TABLE_HOTEL_KEY_ID = "_id";
    private static final String TABLE_HOTEL_KEY_NAME = "name";
    private static final String TABLE_HOTEL_MAIN_IMAGE_NAME = "mainPicture";
    private static final String TABLE_HOTEL_KEY_INFO = "info";
    private static final String TABLE_HOTEL_KEY_EMAIL = "email";
    private static final String TABLE_HOTEL_KEY_TELEPHONE = "telephone";
    private static final String TABLE_HOTEL_KEY_LOCATION = "location";
    private static final String TABLE_HOTEL_KEY_MAIN_PICTURE = "mainPicture";

    private static final String TABLE_RATE_NAME = "Rate";
    private static final String TABLE_RATE_KEY_RATE = "rate";
    private static final String TABLE_RATE_KEY_INFO = "info";

    private static final String TABLE_ROOM_NAME = "Room";
    private static final String TABLE_ROOM_KEY_NAME = "name";
    private static final String TABLE_ROOM_KEY_INFO = "info";
    private static final String TABLE_ROOM_KEY_PRICE = "price";

    private static final String TABLE_IMAGES_NAME = "AdditionalImages";
    private static final String TABLE_IMAGES_KEY_PICTIRE = "picture";

    private static final String TABLE_USER_NAME = "User";
    private static final String TABLE_USER_KEY_TELEPHONE = "telephone";
    private static final String TABLE_USER_KEY_ID = "_id";
    private static final String TABLE_USER_KEY_FULL_NAME = "full_name";
    private static final String TABLE_USER_KEY_EMAIL = "email";

    private static final String TABLE_ALL_PARENT_ID = "parent_id";

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
                    rates, rooms, images,
                    cursor.getString(cursor.getColumnIndex(TABLE_HOTEL_KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(TABLE_HOTEL_KEY_TELEPHONE)),
                    cursor.getString(cursor.getColumnIndex(TABLE_HOTEL_KEY_LOCATION)));
            hotels.add(hotel);
        }
        cursor.close();
        return hotels;
    }

    public void writeFeedbackToDatabase(Rate rate, int hoteLID) {
        ContentValues cv = new ContentValues();
        cv.put(TABLE_RATE_KEY_INFO, rate.getInfo());
        cv.put(TABLE_RATE_KEY_RATE, rate.getRate());
        cv.put(TABLE_ALL_PARENT_ID, hoteLID);
        sqliteDataBase.insert(TABLE_RATE_NAME, null, cv);
    }

    public void deleteFeedbackFromDatabase(Rate rate, int hotelID) {
        sqliteDataBase.delete(TABLE_RATE_NAME, "parent_id = ? and info = ? and rate = ?", new String[]{String.valueOf(hotelID),
                rate.getInfo(), String.valueOf(rate.getRate())});
    }

    public void writeRoomToDatabase(Room room, int hotelID) {
        ContentValues cv = new ContentValues();
        cv.put(TABLE_ROOM_KEY_NAME, room.getName());
        cv.put(TABLE_ROOM_KEY_INFO, room.getInfo());
        cv.put(TABLE_ROOM_KEY_PRICE, room.getPrice());
        cv.put(TABLE_ALL_PARENT_ID, hotelID);
        sqliteDataBase.insert(TABLE_ROOM_NAME, null, cv);
    }

    public void deleteRoomFromDatabase(Room room, int hotelID) {
        sqliteDataBase.delete(TABLE_ROOM_NAME, "parent_id = ? and info = ? and name = ? and price = ?", new String[]{String.valueOf(hotelID),
                room.getInfo(), room.getName(), String.valueOf(room.getPrice())});
    }

    public void writeHotelToDatabase(Hotel hotel) {
        ContentValues cv = new ContentValues();
        cv.put(TABLE_HOTEL_KEY_NAME, hotel.getName());
        cv.put(TABLE_HOTEL_KEY_INFO, hotel.getInfo());
        cv.put(TABLE_HOTEL_KEY_MAIN_PICTURE, hotel.getMainPicture());
        cv.put(TABLE_HOTEL_KEY_TELEPHONE, hotel.getTelephone());
        cv.put(TABLE_HOTEL_KEY_LOCATION, hotel.getLocation());
        cv.put(TABLE_HOTEL_KEY_EMAIL, hotel.getEmail());
        sqliteDataBase.insert(TABLE_HOTEL_NAME, null, cv);
    }

    public void deleteHotelFromDatabase(Hotel hotel) {
        sqliteDataBase.delete(TABLE_HOTEL_NAME, "_id = ?", new String[]{String.valueOf(hotel.getID())});
        sqliteDataBase.delete(TABLE_RATE_NAME, "parent_id = ?", new String[]{String.valueOf(hotel.getID())});
        sqliteDataBase.delete(TABLE_ROOM_NAME, "parent_id = ?", new String[]{String.valueOf(hotel.getID())});
        sqliteDataBase.delete(TABLE_IMAGES_NAME, "parent_id = ?", new String[]{String.valueOf(hotel.getID())});
    }

    @SuppressLint("Range")
    public User checkActiveUser() {
        Cursor cursor = sqliteDataBase.query(TABLE_USER_NAME, null, "is_activated = ?",
                new String[]{"1"}, null, null, null);
        User user = null;
        if (cursor.moveToNext()) {
            user = new User(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("full_name")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("telephone")),
                    cursor.getString(cursor.getColumnIndex("password")));
        }
        cursor.close();
        return user;
    }

    @SuppressLint("Range")
    public User getUser(String telephone, String password) {
        Cursor cursor = sqliteDataBase.query(TABLE_USER_NAME, null, "telephone = ? and password = ?",
                new String[]{telephone, password}, null, null, null);
        User user = null;
        if (cursor.moveToNext()) {
            user = new User(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("full_name")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    telephone, password);
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_activated", "1");
            sqliteDataBase.update(TABLE_USER_NAME, contentValues, "_id" + "=" + cursor.getInt(cursor.getColumnIndex("_id")), null);
        }
        cursor.close();
        return user;
    }

    @SuppressLint("Range")
    public ArrayList<User> getAllUsers() {
        Cursor cursor = sqliteDataBase.query(TABLE_USER_NAME, null, null,
                null, null, null, null);
        ArrayList<User> users = new ArrayList<>(10);
        while (cursor.moveToNext()) {
            users.add(new User(cursor.getInt(cursor.getColumnIndex(TABLE_USER_KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(TABLE_USER_KEY_FULL_NAME)),
                    cursor.getString(cursor.getColumnIndex(TABLE_USER_KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(TABLE_USER_KEY_TELEPHONE))));
        }
        cursor.close();
        return users;
    }

    @SuppressLint("Range")
    public User getUser(int id) {
        Cursor cursor = sqliteDataBase.query(TABLE_USER_NAME, null, "_id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        User user = null;
        if (cursor.moveToNext()) {
            user = new User(id,
                    cursor.getString(cursor.getColumnIndex("full_name")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("telephone")),
                    cursor.getString(cursor.getColumnIndex("password")));
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_activated", "1");
            sqliteDataBase.update(TABLE_USER_NAME, contentValues, "_id" + "=" + id, null);
        }
        cursor.close();
        return user;
    }

    public User createUser(String fullName, String email, String telephone, String password) {
        ContentValues cv = new ContentValues();
        cv.put("full_name", fullName);
        cv.put("telephone", telephone);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("is_activated", "1");
        long id = sqliteDataBase.insert(TABLE_USER_NAME, null, cv);
        return new User((int) id, fullName, email, telephone, password);
    }

    public void deleteUserFromDatabase(int id) {
        sqliteDataBase.delete(TABLE_USER_NAME, "_id = " + id, null);
    }

    @SuppressLint("Range")
    public void leftFromAccount(int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_activated", "0");
        sqliteDataBase.update(TABLE_USER_NAME, contentValues, "_id" + "=" + id, null);
    }

    @SuppressLint("Range")
    public int findUserByTelephone(String telephone) {
        Cursor cursor = sqliteDataBase.query(TABLE_USER_NAME, null, "telephone = ?",
                new String[]{telephone}, null, null, null);
        int userId = -1;
        if (cursor.moveToNext()) {
            userId = cursor.getInt(cursor.getColumnIndex(TABLE_USER_KEY_ID));
        }
        cursor.close();
        return userId;
    }

    public void userUpdatePassword(int id, String password) {
        ContentValues cv = new ContentValues();
        cv.put("password", password);
        sqliteDataBase.update(TABLE_USER_NAME, cv, "_id" + "=" + id, null);
    }

    public void userUpdate(int id, String fullName, String email, String telephone) {
        ContentValues cv = new ContentValues();
        cv.put("full_name", fullName);
        cv.put("telephone", telephone);
        cv.put("email", email);
        sqliteDataBase.update(TABLE_USER_NAME, cv, "_id" + "=" + id, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
