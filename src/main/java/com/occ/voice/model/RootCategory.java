package com.occ.voice.model;

import java.util.List;

public class RootCategory {

List<CategoryDetails> items;

public List<CategoryDetails> getItems() {
	return items;
}

public void setItems(List<CategoryDetails> items) {
	this.items = items;
}

@Override
public String toString() {
	return "RootCategory [items=" + items + "]";
}

}
