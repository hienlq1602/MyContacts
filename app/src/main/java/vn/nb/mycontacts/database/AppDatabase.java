package vn.nb.mycontacts.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vn.nb.mycontacts.entity.Booking;
import vn.nb.mycontacts.entity.Contact;
import vn.nb.mycontacts.entity.Report;
import vn.nb.mycontacts.entity.UserInfo;

@Database(entities = {Contact.class, UserInfo.class, Booking.class, Report.class}, version = 23)

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ContactDao contactDao();

    public abstract UserDao userDao();

    public abstract BookingDao bookingDao();

    public abstract ReportDao reportDao();


    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "database.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
