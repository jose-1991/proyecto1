package org.jflores.project.models;

import java.util.Objects;

public class StateAndQuantity {
    private String state;
    private int quantity;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateAndQuantity stateAndQuantity = (StateAndQuantity) o;
        return Objects.equals(this.state, stateAndQuantity.state);
    }

    @Override
    public String toString() {
        return "state= "+ state +
                "quantity= " + quantity;
    }
}
