package vn.nb.mycontacts.entity;

import androidx.room.Embedded;

public class ContactWithInteractionCount {
    @Embedded
    private Contact contact;

    private int interactionCount;

    public ContactWithInteractionCount(Contact contact, int interactionCount) {
        this.contact = contact;
        this.interactionCount = interactionCount;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public int getInteractionCount() {
        return interactionCount;
    }

    public void setInteractionCount(int interactionCount) {
        this.interactionCount = interactionCount;
    }
}
