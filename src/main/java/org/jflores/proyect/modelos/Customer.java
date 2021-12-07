package org.jflores.proyect.modelos;

import java.util.Objects;

public class Customer {

    private int customer_ID;
    private String cName;
    private String country;
    private String state;
    private String city;
    private Integer postalCode;


    public int getcustomer_ID() {
        return customer_ID;
    }

    public void setcustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
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
        Customer customer = (Customer) o;
        return Objects.equals(cName, customer.cName) && Objects.equals(country, customer.country) && Objects.equals(state, customer.state) && Objects.equals(city, customer.city) && Objects.equals(postalCode, customer.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cName, country, state, city, postalCode);
    }

    @Override
    public String toString() {
        return  customer_ID + " | " +
                cName + " | " +
                country + " | " +
                state + " | " +
                city + " | " +
                postalCode ;
    }
}
