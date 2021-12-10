package org.jflores.proyect.modelos;

import java.util.Objects;

public class Customer {

    private String customer_ID;
    private String cName;



    public String getcustomer_ID() {
        return customer_ID;
    }

    public void setcustomer_ID(String customer_ID) {
        this.customer_ID = customer_ID;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(cName, customer.cName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cName);
    }

    @Override
    public String toString() {
        return  customer_ID + " | " +
                cName + "\n";

    }
}
