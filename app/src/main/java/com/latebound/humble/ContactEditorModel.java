package com.latebound.humble;

/**
 * Created by latebound on 11/29/14.
 */
public interface ContactEditorModel {
    void whenContactInfoChanged(Runnable listener);

    String contactName();

    String contactEmail();

    void setContactName(String contactName);

    void setContactEmail(String contactEmail);

    void save();
}
