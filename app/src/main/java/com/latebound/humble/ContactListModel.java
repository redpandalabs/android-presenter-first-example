package com.latebound.humble;

import java.util.Collection;
import java.util.List;

/**
 * Created by latebound on 11/28/14.
 */
public interface ContactListModel {
    void whenContactsChanged(Runnable listener);

    Collection<Contact> contacts();

    void setContacts(List<Contact> contacts);

    void selectByIndex(int index);

    Contact selectedContact();

    void whenSelectionChanged(Runnable listener);
}
