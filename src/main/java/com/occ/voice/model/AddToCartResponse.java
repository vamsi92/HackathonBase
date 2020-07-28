package com.occ.voice.model;

public class AddToCartResponse {
	
 PriceInfo priceInfo;

public PriceInfo getPriceInfo() {
	return priceInfo;
}

public void setPriceInfo(PriceInfo priceInfo) {
	this.priceInfo = priceInfo;
}

@Override
public String toString() {
	return "AddToCartResponse [priceInfo=" + priceInfo + "]";
}

}
