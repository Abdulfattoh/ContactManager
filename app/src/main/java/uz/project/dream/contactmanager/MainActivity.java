package uz.project.dream.contactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import uz.project.dream.contactmanager.Data.DatabaseHandler;
import uz.project.dream.contactmanager.Model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        //Ma'lumotlarni qo'shish
        Log.d("kiritish","Ma'lumot kiritish boshlandi...");
        db.addContact(new User("Shoxrux", "977656666"));
        db.addContact(new User("Sobirjon", "991235555"));
        db.addContact(new User("Qodir", "991235554"));
        db.addContact(new User("Nodir", "991235553"));
        db.addContact(new User("Kamron", "991235552"));
        db.addContact(new User("Anvar", "991235551"));
        db.addContact(new User("Islombek", "991235550"));


        User firstUser = db.getContact(16);
//        firstUser.setName("Sobir");
//        firstUser.setPhone_number("000000");

        //update
//        int newContact = db.updateContact(firstUser);
        db.deleteContact(firstUser);
//        Log.d("One Contact: ", "Update : " + String.valueOf(newContact) + "Name : " + firstUser.getName());

//         Barcha ma'lumotlarni ko'rish
        List<User> userList = db.getAllContacts();

        for (User user : userList){

            String log = "ID: " + user.getId() + ", Name: " + user.getName() + ", Number: " + user.getPhone_number();

            Log.d("Foydalanuvchilar : ", log);
        }
    }
}
