package vn.nb.mycontacts.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vn.nb.mycontacts.entity.Booking;
import vn.nb.mycontacts.entity.BookingWithContact;

@Dao
public interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Booking booking);

    @Query("SELECT * FROM booking INNER JOIN contact ON booking.contactId = contact.contactId WHERE booking.userId =:id ORDER BY bookingTime ASC")
    List<BookingWithContact> getAllBookingsWithContact(int id);

    @Update
    void update(Booking booking);

    @Query("SELECT COUNT(*) FROM booking WHERE bookingTime >= :startTime AND bookingTime <= :endTime")
    int getItemCountInTimeRange(long startTime, long endTime);
}
