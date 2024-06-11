package vn.nb.mycontacts.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import vn.nb.mycontacts.entity.Contact;
import vn.nb.mycontacts.entity.ContactWithTotalBookings;

@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Contact contact);

    @Query("SELECT * FROM contact WHERE userId =:id ORDER BY nickName ASC")
    List<Contact> getAllContact(int id);
    @Query("SELECT * FROM contact WHERE userId =:id ORDER BY nickName ASC")
    List<Contact> getAllContactByAZ(int id);
    @Query("SELECT * FROM contact WHERE userId =:id ORDER BY nickName DESC")
    List<Contact> getAllContactByZA(int id);

    @Query("SELECT * FROM contact WHERE userId =:id AND active =:active ORDER BY nickName ASC")
    List<Contact> getAllContactByActive(int id, int active);

    @Update
    void update(Contact contact);

    @Query("SELECT * FROM contact WHERE userId=:uid  AND (nickName LIKE '%' || :key || '%' OR phoneNumber LIKE '%' || :key || '%' OR company LIKE '%' || :key || '%') ORDER BY nickName ASC ")
    List<Contact> search(int uid, String key);

    @Query("UPDATE contact SET numberOfCommunication = numberOfCommunication + :point WHERE contactId = :contactId")
    void increaseCount(int contactId,int point);

    @Query("SELECT COUNT(*) FROM contact")
    int getCount();
    @Query("SELECT COUNT(*) FROM contact WHERE timing >= :startTime AND timing <= :endTime")
    int getItemCountInTimeRange(long startTime, long endTime);
    @Query("SELECT * FROM contact WHERE userId =:id ORDER BY numberOfCommunication DESC")
    List<Contact> getContactByTTAZ(int id);
    @Query("SELECT * FROM contact WHERE userId =:id ORDER BY numberOfCommunication ASC")
    List<Contact> getContactByTTZA(int id);




    @Transaction
    @Query("SELECT c.*, COUNT(b.contactId) AS totalBookings " +
            "FROM contact c LEFT JOIN booking b ON c.contactId = b.contactId " +
            "WHERE b.bookingTime BETWEEN :startTime AND :endTime " +
            "GROUP BY c.contactId ORDER BY totalBookings DESC")
    List<ContactWithTotalBookings> getContactsOrderByBookingCountDesc(long startTime, long endTime);


}
