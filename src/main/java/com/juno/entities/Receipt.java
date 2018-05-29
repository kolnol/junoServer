package com.juno.entities;

import java.util.Date;
import java.util.List;

public class Receipt {
    private Company company;
    private Date purchaseDate;
    private List<ShoppingItem> items;
    private double total;
    private PaymentMethod paymentMethod;

    public Receipt(Company company,
                   Date purchaseDate,
                   List<ShoppingItem> items,
                   double total,
                   PaymentMethod paymentMethod) {
        this.company = company;
        this.purchaseDate = purchaseDate;
        this.items = items;
        this.total = total;
        this.paymentMethod = paymentMethod;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<ShoppingItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingItem> items) {
        this.items = items;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
