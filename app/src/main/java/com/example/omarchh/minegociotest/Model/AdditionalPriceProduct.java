package com.example.omarchh.minegociotest.Model;

/**
 * Created by OMAR CHH on 01/10/2017.
 */

public class AdditionalPriceProduct {

    private String cKey;
    private float additionalPrice;

    public String getcKey() {
        return cKey;
    }

    public void setcKey(String cKey) {
        this.cKey = cKey;
    }

    public float getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(int additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public AdditionalPriceProduct() {

    }

    public AdditionalPriceProduct(String cKey, float additionalPrice) {

        this.cKey = cKey;
        this.additionalPrice = additionalPrice;
    }
}
