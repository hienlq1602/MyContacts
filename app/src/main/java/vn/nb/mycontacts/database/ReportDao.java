package vn.nb.mycontacts.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import vn.nb.mycontacts.entity.ContactWithInteractionCount;
import vn.nb.mycontacts.entity.Report;
import vn.nb.mycontacts.entity.ReportTypeCount;

@Dao
public interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    void insert(Report report);


    @Query("SELECT type, COUNT(*) AS totalReports " +
            "FROM report " +
            "WHERE timeStamp BETWEEN :startTime AND :endTime " +
            "GROUP BY type")
    List<ReportTypeCount> getReportTypeCounts(long startTime, long endTime);

//    @Transaction
//    @Query("SELECT c.*, COUNT(r.contactId) AS interactionCount " +
//            "FROM contact c LEFT JOIN report r ON c.contactId = r.contactId " +
//            "WHERE r.timeStamp BETWEEN :startTime AND :endTime " +
//            "GROUP BY c.contactId " +
//            "ORDER BY interactionCount DESC")
//    LiveData<List<ContactWithInteractionCount>> getContactsOrderedByInteractionCountInTimeRangeDESC(long startTime, long endTime);
//


    @Transaction
    @Query("SELECT c.*, COALESCE(COUNT(r.contactId), 0) AS interactionCount " +
            "FROM contact c LEFT JOIN report r ON c.contactId = r.contactId " +
            "AND r.timeStamp BETWEEN :startTime AND :endTime " +
            "GROUP BY c.contactId " +
            "ORDER BY interactionCount DESC")
    LiveData<List<ContactWithInteractionCount>> getContactsOrderedByInteractionCountInTimeRangeDESC(long startTime, long endTime);



    @Transaction
    @Query("SELECT c.*, COALESCE(COUNT(r.contactId), 0) AS interactionCount " +
            "FROM contact c LEFT JOIN report r ON c.contactId = r.contactId " +
            "AND r.timeStamp BETWEEN :startTime AND :endTime " +
            "GROUP BY c.contactId " +
            "ORDER BY interactionCount ASC")
    LiveData<List<ContactWithInteractionCount>> getContactsOrderedByInteractionCountInTimeRangeASC(long startTime, long endTime);

    @Transaction
//    @Query("SELECT c.*, COUNT(r.contactId) AS interactionCount " +
//            "FROM contact c LEFT JOIN report r ON c.contactId = r.contactId " +
//            "WHERE r.timeStamp BETWEEN :startTime AND :endTime " +
//            "GROUP BY c.contactId " +
//            "ORDER BY interactionCount ASC")
//    LiveData<List<ContactWithInteractionCount>> getContactsOrderedByInteractionCountInTimeRangeASC(long startTime, long endTime);


    @Query("SELECT COUNT(*) FROM report WHERE timeStamp BETWEEN :startTime AND :endTime")
    int totalsReportRang(long startTime, long endTime);


}
