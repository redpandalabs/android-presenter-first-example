package com.latebound.humble;

import java.util.Collection;

/**
 * Created by latebound on 11/28/14.
 */
public interface ContactListModel {
    void whenContactsChanged(Runnable listener);

    Collection<Contact> contacts();

    void setContacts(Collection<Contact> contacts);

    void selectByIndex(int index);
}
