package com.latebound.pf;

import android.app.Activity;
import android.os.Bundle;

import java.util.Arrays;

public class ContactApp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);

        ContactListModel listModel = new InMemoryContactListModel();
        ContactListView listView = (ContactListView) getFragmentManager().findFragmentById(R.id.titles);

        new ContactListPresenter(listModel, listView);

        ContactEditorModel editorModel = new InMemoryContactEditorModel();
        ContactEditorView editorView = (ContactEditorView) getFragmentManager().findFragmentById(R.id.details);

        new ContactEditorPresenter(editorModel, editorView);

        listModel.whenSelectionChanged(() -> {
            editorModel.setCurrentContact(listModel.selectedContact());
        });

        editorModel.whenContactSaved(() -> listView.refresh());
    }

    private class ContactListPresenter {
        public ContactListPresenter(ContactListModel model, ContactListView view) {
            model.whenContactsChanged(() -> view.setContacts(model.contacts()));
            view.whenSelectionChanged(() -> model.selectByIndex(view.selectedIndex()));
            view.whenInitialized(() -> model.setContacts(Arrays.asList(ContactBook.CONTACTS)));
        }
    }

    private class ContactEditorPresenter {
        public ContactEditorPresenter(ContactEditorModel model, ContactEditorView view) {
            model.whenContactInfoChanged(() -> {
                view.setName(model.contactName());
                view.setEmail(model.contactEmail());
            });
            view.whenUserSaved(() -> {
                model.setContactName(view.contactName());
                model.setContactEmail(view.contactEmail());
                model.save();
            });
        }
    }
}
