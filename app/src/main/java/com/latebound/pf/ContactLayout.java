package com.latebound.pf;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ContactLayout extends Activity {

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);

        ContactListModel model = new InMemoryContactListModel();
        ContactListView view = (ContactListView) getFragmentManager().findFragmentById(R.id.titles);

        new ContactListPresenter(model, view);

        ContactEditorModel editorModel = new InMemoryContactEditorModel();
        ContactEditorView editorView = (ContactEditorView) getFragmentManager().findFragmentById(R.id.details);

        model.whenSelectionChanged(() -> {
            contact = model.selectedContact();
            editorModel.setCurrentContact(contact);
        });

        editorModel.whenContactInfoChanged(() -> {
            editorView.setName(contact.name());
            editorView.setEmail(contact.email());
        });
    }

    public static class ContactListFragment extends ListFragment implements ContactListView {
        private ArrayAdapter<Contact> adapter;
        private List<Runnable> selectionChanged = new LinkedList<>();
        private List<Runnable> initialized = new LinkedList<>();

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1);
            setListAdapter(adapter);

            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            fire(initialized);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            fire(selectionChanged);
            getListView().setItemChecked(position, true);
        }

        @Override
        public void setContacts(Collection<Contact> contacts) {
            adapter.clear();
            adapter.addAll(contacts);
        }

        @Override
        public int selectedIndex() {
            return getListView().getCheckedItemPosition();
        }

        @Override
        public void whenSelectionChanged(Runnable listener) {
            selectionChanged.add(listener);
        }

        @Override
        public void whenInitialized(Runnable listener) {
            initialized.add(listener);
        }

        private void fire(Collection<Runnable> listeners) {
            for (Runnable l: listeners) {
                l.run();
            }
        }
    }

    public static class ContactEditorFragment extends Fragment implements ContactEditorView {
        private EditText name;
        private EditText email;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.contact_editor_fragment, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            name = (EditText) getActivity().findViewById(R.id.name);
            email = (EditText) getActivity().findViewById(R.id.email);
        }

        @Override
        public void setName(String name) {
            this.name.setText(name);
        }

        @Override
        public void setEmail(String email) {
            this.email.setText(email);
        }

        @Override
        public String contactName() {
            return name.getText().toString();
        }

        @Override
        public String contactEmail() {
            return email.getText().toString();
        }

        @Override
        public void whenUserSaved(Runnable listener) {

        }
    }

    private class ContactListPresenter {
        public ContactListPresenter(ContactListModel model, ContactListView view) {
            model.whenContactsChanged(() -> view.setContacts(model.contacts()));
            view.whenSelectionChanged(() -> model.selectByIndex(view.selectedIndex()));
            view.whenInitialized(() -> model.setContacts(Arrays.asList(Shakespeare.TITLES)));
        }
    }
}
