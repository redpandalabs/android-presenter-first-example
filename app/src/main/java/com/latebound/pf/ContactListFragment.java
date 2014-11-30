package com.latebound.pf;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collection;
import java.util.LinkedList;

/**
* Created by latebound on 11/30/14.
*/
public class ContactListFragment extends ListFragment implements ContactListView {
    private ArrayAdapter<Contact> adapter;
    private Collection<Runnable> selectionChanged = new LinkedList<>();
    private Collection<Runnable> initialized = new LinkedList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1);
        setListAdapter(adapter);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        Events.fire(initialized);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Events.fire(selectionChanged);
        getListView().setItemChecked(position, true);
    }

    @Override
    public void setContacts(Collection<Contact> contacts) {
        adapter.clear();
        adapter.addAll(contacts);
        refresh();
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

    @Override
    public void refresh() {
        adapter.notifyDataSetInvalidated();
    }
}
