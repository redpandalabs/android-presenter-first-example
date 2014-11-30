package com.latebound.humble;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

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
            System.out.println(editorModel.contactEmail());
        });
    }

    public static class ContactListFragment extends ListFragment implements ContactListView {
        private ArrayAdapter<Contact> adapter;
        private LinkedList<Runnable> selectionChanged = new LinkedList<>();
        private LinkedList<Runnable> initialized = new LinkedList<>();

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

        private void fire(Collection<Runnable> listeners) {
            for (Runnable l: listeners) {
                l.run();
            }
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
    }

    public static class DetailsFragment extends Fragment implements ContactEditorView {
        private TextView name;
        private TextView email;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (container == null) {
                return null;
            }

            ScrollView scroller = new ScrollView(getActivity());
            int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    4, getActivity().getResources().getDisplayMetrics());

            name = new TextView(getActivity());
            name.setPadding(padding, padding, padding, padding);
            scroller.addView(name);

            email = new TextView(getActivity());
            email.setPadding(padding, padding, padding, padding);
            scroller.addView(email);

            return scroller;
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
