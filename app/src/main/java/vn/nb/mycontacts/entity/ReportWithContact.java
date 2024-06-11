package vn.nb.mycontacts.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ReportWithContact {
    @Embedded
    public Report report;

    @Relation(parentColumn = "contactId", entityColumn = "id")
    public Contact contact;
}
