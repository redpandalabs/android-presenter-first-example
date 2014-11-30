package com.latebound.pf;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by latebound on 11/30/14.
 */
public class InMemoryContactEditorModel implements ContactEditorModel {
    private Contact contact;
    private Collection<Runnable> contactInfoChanged = new LinkedList<>();
    private Collection<Runnable> contactSaved = new LinkedList<>();

    @Override
    public void whenContactInfoChanged(Runnable listener) {
        contactInfoChanged.add(listener);
    }

    @Override
    public String contactName() {
        return contact.name();
    }

    @Override
    public String contactEmail() {
        return contact.email();
    }

    @Override
    public void setContactName(String contactName) {
        contact.setName(contactName);
    }

    @Override
    public void setContactEmail(String contactEmail) {
        contact.setEmail(contactEmail);
    }

    @Override
    public void setCurrentContact(Contact contact) {
        this.contact = contact;
        Events.fire(contactInfoChanged);
    }

    @Override
    public void save() {
        Events.fire(contactSaved);
    }

    @Override
    public void whenContactSaved(Runnable listener) {
        contactSaved.add(listener);
    }
}
