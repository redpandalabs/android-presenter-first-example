package com.latebound.pf;

/**
 * Created by latebound on 11/29/14.
 */
public interface ContactEditorView {
    void setName(String name);

    void setEmail(String email);

    String contactName();

    String contactEmail();

    void whenUserSaved(Runnable listener);
}
