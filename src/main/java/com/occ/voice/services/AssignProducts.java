package com.occ.voice.services;

import java.util.List;

public class AssignProducts {

  List<String> products;
  String collection;
  int productCount=1;
  String type = "appendProducts";

public List<String> getProducts() {
	return products;
}
public void setProducts(List<String> products) {
	this.products = products;
}
public String getCollection() {
	return collection;
}
public void setCollection(String collection) {
	this.collection = collection;
}
public int getProductCount() {
	return productCount;
}
public void setProductCount(int productCount) {
	this.productCount = productCount;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
@Override
public String toString() {
	return "AssignProducts [products=" + products + ", collection=" + collection + ", productCount=" + productCount
			+ ", type=" + type + "]";
}
  
  

}
