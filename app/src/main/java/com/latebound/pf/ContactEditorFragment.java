package com.latebound.pf;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.LinkedList;

/**
* Created by latebound on 11/30/14.
*/
public class ContactEditorFragment extends Fragment implements ContactEditorView {
    private TextView name;
    private TextView email;
    private Collection<Runnable> userSaved = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_editor_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = (TextView) getActivity().findViewById(R.id.name);
        email = (TextView) getActivity().findViewById(R.id.email);
        getActivity().findViewById(R.id.update).setOnClickListener((View view) -> Events.fire(userSaved));
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
        userSaved.add(listener);
    }

}
