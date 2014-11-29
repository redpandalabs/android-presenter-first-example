package com.latebound.humble;

/**
 * Created by latebound on 11/29/14.
 */
public class Contact {
    private final String name;
    private final String email;

    public Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    @Override
    public String toString() {
        return name;
    }
}
