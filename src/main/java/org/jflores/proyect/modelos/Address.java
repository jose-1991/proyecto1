package org.jflores.proyect.modelos;

import java.util.Objects;

public class Address {
    private int address_ID;
    private String country;
    private String state;
    private String city;
    private Integer postalCode;

    public int getAddress_ID() {
        return address_ID;
    }

    public void setAddress_ID(int address_ID) {
        this.address_ID = address_ID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) && Objects.equals(state, address.state) && Objects.equals(city, address.city) && Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, state, city, postalCode);
    }

    @Override
    public String toString() {
        return address_ID + " | " +
                country + " | " +
                state + " | " +
                city + " | " +
                postalCode + "\n";
    }
}
