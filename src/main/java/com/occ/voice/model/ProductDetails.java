package com.occ.voice.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetails {

 private boolean active;
 private String brand;
 private String description;
 private String displayName;
 private String id;
 private String repositoryId;
 private String x_colorName;
 private int listPrice;
 private String route;
 private String primaryFullImageURL;
 private List<Map<String, Object>> childSKUs;
 private List<Map<String,Object>> productVariantOptions;
 
public boolean isActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
}
public String getBrand() {
	return brand;
}
public void setBrand(String brand) {
	this.brand = brand;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getDisplayName() {
	return displayName;
}
public void setDisplayName(String displayName) {
	this.displayName = displayName;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getRepositoryId() {
	return repositoryId;
}
public void setRepositoryId(String repositoryId) {
	this.repositoryId = repositoryId;
}
public String getX_colorName() {
	return x_colorName;
}
public void setX_colorName(String x_colorName) {
	this.x_colorName = x_colorName;
}
public int getListPrice() {
	return listPrice;
}
public void setListPrice(int listPrice) {
	this.listPrice = listPrice;
}


public List<Map<String, Object>> getChildSKUs() {
	return childSKUs;
}
public void setChildSKUs(List<Map<String, Object>> childSKUs) {
	this.childSKUs = childSKUs;
}
public List<Map<String, Object>> getProductVariantOptions() {
	return productVariantOptions;
}
public void setProductVariantOptions(List<Map<String, Object>> productVariantOptions) {
	this.productVariantOptions = productVariantOptions;
}

public String getRoute() {
	return route;
}
public void setRoute(String route) {
	this.route = route;
}
public String getPrimaryFullImageURL() {
	return primaryFullImageURL;
}
public void setPrimaryFullImageURL(String primaryFullImageURL) {
	this.primaryFullImageURL = primaryFullImageURL;
}

@Override
public String toString() {
	return "ProductDetails [active=" + active + ", brand=" + brand + ", description=" + description + ", displayName="
			+ displayName + ", id=" + id + ", repositoryId=" + repositoryId + ", x_colorName=" + x_colorName
			+ ", listPrice=" + listPrice + ", route=" + route + ", primaryFullImageURL=" + primaryFullImageURL
			+ ", childSKUs=" + childSKUs + ", productVariantOptions=" + productVariantOptions + "]";
}

}
