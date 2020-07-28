package com.occ.voice.model;

import java.util.List;

public class Category {

 private List<ProductDetails> items;
 

 public List<ProductDetails> getItems() {
	return items;
}

 public void setItems(List<ProductDetails> items) {
	this.items = items;
}

 @Override
 public String toString() {
	return "Category [items=" + items + "]";
 }
 
}
