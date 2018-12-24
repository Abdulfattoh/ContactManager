package uz.project.dream.contactmanager.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import uz.project.dream.contactmanager.Model.User;
import uz.project.dream.contactmanager.Util.Util;


public class DatabaseHandler extends SQLiteOpenHelper{

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    // create tables - jadval hosil qilib olishimiz kerak
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL - Structured Query Language
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT,"
                + Util.KEY_PHONE_NUMBER + " TEXT" + ")";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }

    //    CRUD - Create, Read, Update, Delete

    //Add contact
    public void addContact(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, user.getName());
        values.put(Util.KEY_PHONE_NUMBER, user.getPhone_number());

//        Insert row - jadvalga qo'shish amaliyoti
        db.insert(Util.TABLE_NAME, null, values);
        db.close(); //ma'lumotlar ombori bilan aloqani uzadi

    }

    //Get contact - bitta foydalanuvchi ma'lumotlarini olish uchun
    public User getContact(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[] { Util.KEY_ID,
            Util.KEY_NAME, Util.KEY_PHONE_NUMBER}, Util.KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return user;
    }

    // Get all contacts - barcha foydalanuvchilar ma'lumotlarini olish uchun
    public List<User> getAllContacts() {

        SQLiteDatabase db = this.getReadableDatabase();

        List<User> userList = new ArrayList<>();

        //Barcha ma'lumotlar olib chiqish so'rov yasaymiz
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        // do while metodi orqali ma'lumotlarni qabul qilib olamiz
        if (cursor.moveToFirst()){
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setPhone_number(cursor.getString(2));

                // ochilgan bo'sh massivga ma'lumotlarni yig'amiz
                userList.add(user);

            }while (cursor.moveToNext());
        }

        return userList;
    }

    //update - ma'lumotlarni o'zgartirish

    public int updateContact(User user){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, user.getName());
        values.put(Util.KEY_PHONE_NUMBER, user.getPhone_number());

        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[] {String.valueOf(user.getId())});

    }

    //delete - ma'lumotlarni o'chirish

    public void deleteContact(User user){

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(Util.TABLE_NAME, Util.KEY_ID + " =?",
                new String[] {String.valueOf(user.getId())});

        db.close();
    }


}
