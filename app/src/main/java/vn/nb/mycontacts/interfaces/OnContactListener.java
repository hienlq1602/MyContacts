package vn.nb.mycontacts.interfaces;

import vn.nb.mycontacts.entity.Contact;

public interface OnContactListener {
    void onItemClick(Contact contact,int p);
}
