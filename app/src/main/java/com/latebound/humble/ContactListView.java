package com.latebound.humble;

import java.util.Collection;

/**
 * Created by latebound on 11/28/14.
 */
public interface ContactListView {
    void setContacts(Collection<Contact> contacts);

    int selectedIndex();

    void whenSelectionChanged(Runnable listener);
}
