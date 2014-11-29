package com.latebound.humble;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by latebound on 11/28/14.
 */
public class InMemoryContactListModel implements ContactListModel {
    private Collection<Contact> contacts = new ArrayList<>();
    private Collection<Runnable> contactsChanged = new ArrayList<>();

    @Override
    public void whenContactsChanged(Runnable listener) {
        contactsChanged.add(listener);
    }

    @Override
    public Collection<Contact> contacts() {
        return contacts;
    }

    @Override
    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
        fireContactsChanged();
    }

    @Override
    public void selectByIndex(int index) {
        System.out.println("... SELECTED ... " + index);
    }

    private void fireContactsChanged() {
        for (Runnable l: contactsChanged) {
            l.run();
        }
    }
}
