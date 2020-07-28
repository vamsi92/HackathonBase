package com.occ.voice.model;

public class PriceInfo {

 double amount;
 double total;
 double shipping;
 double shippingSurchargeValue;
 double tax;
 double subTotal;
 String currencyCode;
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public double getTotal() {
	return total;
}
public void setTotal(double total) {
	this.total = total;
}
public double getShipping() {
	return shipping;
}
public void setShipping(double shipping) {
	this.shipping = shipping;
}
public double getShippingSurchargeValue() {
	return shippingSurchargeValue;
}
public void setShippingSurchargeValue(double shippingSurchargeValue) {
	this.shippingSurchargeValue = shippingSurchargeValue;
}
public double getTax() {
	return tax;
}
public void setTax(double tax) {
	this.tax = tax;
}
public double getSubTotal() {
	return subTotal;
}
public void setSubTotal(double subTotal) {
	this.subTotal = subTotal;
}
public String getCurrencyCode() {
	return currencyCode;
}
public void setCurrencyCode(String currencyCode) {
	this.currencyCode = currencyCode;
}

@Override
public String toString() {
	return "PriceInfo [amount=" + amount + ", total=" + total + ", shipping=" + shipping + ", shippingSurchargeValue="
			+ shippingSurchargeValue + ", tax=" + tax + ", subTotal=" + subTotal + ", currencyCode=" + currencyCode
			+ "]";
}
 
 
}
