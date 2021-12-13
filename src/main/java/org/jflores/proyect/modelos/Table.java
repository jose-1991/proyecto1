package org.jflores.proyect.modelos;

public enum Table {
    CUSTOMER {
        @Override
        public String toString() {
            return "INSERT INTO store.customer(customer_ID, cName)" +
                    "VALUES(?,?)";
        }
    },
    ADDRESS {
        @Override
        public String toString() {
            return "INSERT INTO store.address(address_ID, country, state, city, postalCode) " +
                    "VALUES(?,?,?,?,?)";
        }
    },

    PRODUCT {
        @Override
        public String toString() {
            return "INSERT INTO store.product(product_ID, category, sub_category, pName) " +
                    "VALUES(?,?,?,?)";
        }
    },

    ORDER {
        @Override
        public String toString() {
            return "INSERT INTO store.order(order_ID, orderDate, customer_ID, address_ID, product_ID, price, quantity, discount, total, profit)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?)";
        }
    }
}
