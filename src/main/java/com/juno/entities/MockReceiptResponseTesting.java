package com.juno.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockReceiptResponseTesting extends Receipt {
    private String text;

    public MockReceiptResponseTesting(String text) {

        super(new Company("Test Company", "image link"),
                new Date(),
                new ArrayList<>(),
                13.50,
                PaymentMethod.CARD);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
