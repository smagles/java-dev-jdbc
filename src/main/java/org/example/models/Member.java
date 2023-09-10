package org.example.models;

public class Member {
    private int id;
    private String name;
    private String email;
    private String buildingAddress;
    private int flatNumber;
    private float flatSquare;

    public Member() {
    }

    public int getId() {
        return id;
    }

    public Member setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Member setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Member setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public Member setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
        return this;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    public Member setFlatNumber(int flatNumber) {
        this.flatNumber = flatNumber;
        return this;
    }

    public float getFlatSquare() {
        return flatSquare;
    }

    public Member setFlatSquare(float flatSquare) {
        this.flatSquare = flatSquare;
        return this;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", buildingAddress='" + buildingAddress + '\'' +
                ", flatNumber=" + flatNumber +
                ", flatSquare=" + flatSquare +
                '}';
    }
}
