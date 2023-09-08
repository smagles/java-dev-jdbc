package org.example.data;

public class Member {
    private int id;
    private String name;
    public int getId() {
        return id;
    }
    Member setId(final int newValue) {
        id =newValue;
        return this;
    }

    public String getName() {
        return name;
    }

    Member setName (final String newName) {
        name=newName;
        return this;
    }


}
