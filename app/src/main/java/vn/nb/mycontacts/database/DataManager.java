package vn.nb.mycontacts.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import vn.nb.mycontacts.UserManager;
import vn.nb.mycontacts.entity.Booking;
import vn.nb.mycontacts.entity.BookingWithContact;
import vn.nb.mycontacts.entity.Contact;
import vn.nb.mycontacts.entity.ContactWithInteractionCount;
import vn.nb.mycontacts.entity.ContactWithTotalBookings;
import vn.nb.mycontacts.entity.Report;
import vn.nb.mycontacts.entity.ReportTypeCount;
import vn.nb.mycontacts.entity.UserInfo;

public class DataManager {

    private ContactDao contactDao;
    private UserDao userDao;
    private BookingDao bookingDao;
    private ReportDao reportDao;


    public DataManager(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        contactDao = appDatabase.contactDao();
        userDao = appDatabase.userDao();
        bookingDao = appDatabase.bookingDao();
        reportDao = appDatabase.reportDao();


    }

    public void insertContact(Contact contact) {
        contactDao.insert(contact);
    }

    public int insertUser(UserInfo userInfo) {
        UserInfo us = userDao.getUser(userInfo.getPhoneNumber());
        if (us != null) {
            return 0;
        } else {
            long id = userDao.insert(userInfo);
            if (id != 0) {
                return 2;
            }
            return 1;

        }

    }

    public UserInfo getUserInfo(String tk, String mk) {
        return userDao.getUser(tk, mk);
    }

    public void updateContact(Contact contact) {
        contactDao.update(contact);
    }


    public List<Contact> getAllContact() {
        return contactDao.getAllContact(UserManager.getInstance().getPerson().getId());
    }

    public List<Contact> getContactBySort(int sort) {
        switch (sort) {
            case 1: {
                return contactDao.getAllContactByZA(UserManager.getInstance().getPerson().getId());
            }
            case 2: {
                return contactDao.getAllContactByActive(UserManager.getInstance().getPerson().getId(), 2);
                //quy uoc 0 1 2 (normal,love,block)

            }
            case 3: {
                return contactDao.getAllContactByActive(UserManager.getInstance().getPerson().getId(), 1);
                //quy uoc 0 1 2 (normal,love,block)

            }
            default: {
                return contactDao.getAllContactByAZ(UserManager.getInstance().getPerson().getId());
            }
        }

    }

    public List<Contact> searchContact(String key) {
        return contactDao.search(UserManager.getInstance().getPerson().getId(), key);
    }

    public void insertBooking(Booking booking) {
        bookingDao.insert(booking);
    }

    public List<BookingWithContact> getBooking() {
        return bookingDao.getAllBookingsWithContact(UserManager.getInstance().getPerson().getId());
    }

    public void updateBooking(Booking booking) {
        bookingDao.update(booking);

    }


    public void insertReport(Report report, int point) {
        reportDao.insert(report);
        contactDao.increaseCount(report.getContactId(), point);
    }


    public List<ContactWithTotalBookings> getContactWithTotalBookings(long startTime, long endTime) {
        return contactDao.getContactsOrderByBookingCountDesc(startTime, endTime);
    }

    public int totalsContact() {
        return contactDao.getCount();
    }

    public int totalsContactAdd(long start, long end) {
        return contactDao.getItemCountInTimeRange(start, end);
    }

    public int totalsBookingRang(long start, long end) {
        return bookingDao.getItemCountInTimeRange(start, end);
    }
    public int totalsReport(long start, long end) {
        return reportDao.totalsReportRang(start, end);
    }


    public List<ReportTypeCount> reportTypeCounts(long start, long end) {
        return reportDao.getReportTypeCounts(start, end);
    }

    public LiveData<List<ContactWithInteractionCount> > contactWithInteractionCountsDESC(long start, long end) {
        return reportDao.getContactsOrderedByInteractionCountInTimeRangeDESC(start, end);
    }
    public LiveData<List<ContactWithInteractionCount> > contactWithInteractionCountsASC(long start, long end) {
        return reportDao.getContactsOrderedByInteractionCountInTimeRangeASC(start, end);
    }
}
