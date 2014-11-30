package com.latebound.pf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by latebound on 11/28/14.
 */
public class InMemoryContactListModel implements ContactListModel {
    private List<Contact> contacts = new ArrayList<>();
    private Collection<Runnable> contactsChanged = new ArrayList<>();
    private Collection<Runnable> selectionChanged = new ArrayList<>();
    private int selectedIndex;

    @Override
    public void whenContactsChanged(Runnable listener) {
        contactsChanged.add(listener);
    }

    @Override
    public void whenSelectionChanged(Runnable listener) {
        selectionChanged.add(listener);
    }

    @Override
    public Collection<Contact> contacts() {
        return contacts;
    }

    @Override
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        fire(contactsChanged);
    }

    @Override
    public void selectByIndex(int index) {
        selectedIndex = index;
        fire(selectionChanged);
    }

    @Override
    public Contact selectedContact() {
        return contacts.get(selectedIndex);
    }

    private void fire(Collection<Runnable> listeners) {
        for (Runnable l: listeners) {
            l.run();
        }
    }
}
