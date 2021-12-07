package org.jflores.proyect.modelos;

import java.util.Objects;

public class Product {

    private int product_ID;
    private String category;
    private String sub_category;
    private String pName;
    private Double sales;



    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Double getSales() {
        return sales;
    }

    public void setSales(Double sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(category, product.category) && Objects.equals(sub_category, product.sub_category) && Objects.equals(pName, product.pName) && Objects.equals(sales, product.sales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, sub_category, pName, sales);
    }

    @Override
    public String toString() {
        return product_ID + " | " +
                category + " | " +
                sub_category + " | " +
                pName + " | " +
                sales;
    }
}
