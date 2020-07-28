package com.occ.voice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDetails {

private boolean active;
private String description;
private String displayName;
private String id;
private String longDescription;
private String repositoryId;
private String route;
  

public boolean isActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
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
public String getLongDescription() {
	return longDescription;
}
public void setLongDescription(String longDescription) {
	this.longDescription = longDescription;
}
public String getRepositoryId() {
	return repositoryId;
}
public void setRepositoryId(String repositoryId) {
	this.repositoryId = repositoryId;
}
public String getRoute() {
	return route;
}
public void setRoute(String route) {
	this.route = route;
}
@Override
public String toString() {
	return "CategoryDetails [active=" + active + ", description=" + description + ", displayName=" + displayName
			+ ", id=" + id + ", longDescription=" + longDescription + ", repositoryId=" + repositoryId + ", route="
			+ route + "]";
}
	
}
